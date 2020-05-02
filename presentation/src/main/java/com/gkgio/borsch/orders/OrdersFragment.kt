package com.gkgio.borsch.orders

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.orders.detail.OrderDetailDialogListener
import com.gkgio.borsch.orders.detail.OrderDetailFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.empty_error_view.*
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.android.synthetic.main.fragment_orders.toolbar

class OrdersFragment : BaseFragment<OrdersViewModel>(), OrderDetailDialogListener {

    companion object {
        private const val MAX_SCHEDULE_SHEET_ALPHA = 0.5f
    }

    private var orderRecyclerAdapter: OrderRecyclerAdapter? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ordersViewModel
    }

    override fun onResume() {
        super.onResume()
        setupAlphaOnRestoreState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrderRv()

        swipeToRefreshLayout.setOnRefreshListener {
            viewModel.loadOrderData()
        }

        findCookerButton.setDebounceOnClickListener {
            viewModel.onFindCookerButtonClick()
        }

        viewModel.state.observeValue(this) { state ->
            swipeToRefreshLayout.isRefreshing = state.isLoading
            emptyErrorView.isVisible = state.isInitialError && state.orderDataList == null
            ordersEmptyView.isVisible = state.orderDataList.isNullOrEmpty() && !state.isInitialError

            state.orderDataList?.let {
                orderRecyclerAdapter?.setOrdersList(it)
            }
        }

        viewModel.openOrderDetailSheet.observeValue(this) {
            addFragmentToContainerBottomSheet(OrderDetailFragment.newInstance(it))
        }

        updateEmptyBtn.setDebounceOnClickListener {
            viewModel.loadOrderData()
        }

        bottomSheetBehavior =
            BottomSheetBehavior.from(view.findViewById<View>(R.id.orderDetailSheet))
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, slide: Float) {
                alphaView.alpha = slide / 2
            }

            override fun onStateChanged(p0: View, state: Int) {
                alphaView.isVisible = state != BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        coordinatorOrderDetail.doOnPreDraw {
            orderDetailSheet.layoutParams.height =
                coordinatorOrderDetail.height - (toolbar.height * 1.2).toInt()
        }

        setupLockListeners()
    }

    override fun onBackClick() {
        when {
            bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            else -> {
                super.onBackClick()
            }
        }
    }

    override fun onCollapseClick() = onBackClick()

    private fun initOrderRv() {
        orderRecyclerAdapter = OrderRecyclerAdapter(
            { orderId ->
                viewModel.onOrderDetailOpenClick(orderId)
            },
            { orderId ->
                viewModel.onOpenChatClick(orderId)
            }
        )

        rvOrders.adapter = orderRecyclerAdapter
        rvOrders.layoutManager = LinearLayoutManager(context)
    }

    private fun setupAlphaOnRestoreState() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED
            && resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ) {
            alphaView.alpha = MAX_SCHEDULE_SHEET_ALPHA
            alphaView.isVisible = true
        }
    }

    private fun setupLockListeners() {
        alphaView.setOnTouchListener { _, _ ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun addFragmentToContainerBottomSheet(fragment: Fragment) {
        val fm = childFragmentManager.beginTransaction()
        with(fm) {
            replace(
                R.id.sheetContainer,
                fragment,
                fragment::class.java.name
            )
            commitNowAllowingStateLoss()
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}