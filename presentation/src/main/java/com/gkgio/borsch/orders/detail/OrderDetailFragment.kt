package com.gkgio.borsch.orders.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.orders.OrderStatus
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import com.gkgio.borsch.view.RecyclerViewVerticalLineDivider
import kotlinx.android.synthetic.main.fragment_order_detail.*
import java.lang.StringBuilder

interface OrderDetailDialogListener {
    fun onCollapseClick()
}

class OrderDetailFragment : BaseFragment<OrderDetailViewModel>() {

    companion object {
        fun newInstance(orderId: String) =
            OrderDetailFragment().apply {
                this.orderId = orderId
            }
    }

    private var orderId: String by FragmentArgumentDelegate()

    private var listener: OrderDetailDialogListener? = null

    private var orderDetailRecyclerAdapter: OrderDetailRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_order_detail

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.orderDetailViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = when {
            parentFragment is OrderDetailDialogListener -> parentFragment as OrderDetailDialogListener
            context is OrderDetailDialogListener -> context
            else -> throw IllegalStateException("Either parentFragment or context must implement FoodItemDialogListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(orderId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setLeftIconClickListener {
            listener?.onCollapseClick()
        }

        initFoodRv()

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isLoading
            emptyErrorView.isVisible = state.isInitialError

            state.orderDetailDataUi?.let { orderData ->
                with(orderData.order) {
                    toolbar.setTitle(getString(R.string.order_number_format, slug))

                    foods?.let {
                        orderDetailRecyclerAdapter?.setOrdersList(it)
                    }

                    fullPriceTv.text = getString(R.string.order_price_filter, orderPrice)
                    when (status) {
                        OrderStatus.ACCEPTED.type -> {
                            statusTv.setBackgroundResource(R.drawable.bg_status_green)
                            statusTv.text = OrderStatus.ACCEPTED.value
                        }
                        OrderStatus.COOKING.type -> {
                            statusTv.setBackgroundResource(R.drawable.bg_status_green)
                            statusTv.text = OrderStatus.COOKING.value
                        }
                        OrderStatus.CREATED.type -> {
                            statusTv.setBackgroundResource(R.drawable.bg_status_green)
                            statusTv.text = OrderStatus.CREATED.value
                        }
                        OrderStatus.REJECTED.type -> {
                            statusTv.setBackgroundResource(R.drawable.bg_status_red)
                            statusTv.text = OrderStatus.REJECTED.value
                        }
                        OrderStatus.CANCELED.type -> {
                            statusTv.setBackgroundResource(R.drawable.bg_status_red)
                            statusTv.text = OrderStatus.CANCELED.value
                        }
                        OrderStatus.COMPLETED.type -> {
                            statusTv.setBackgroundResource(R.drawable.bg_status_grey)
                            statusTv.text = OrderStatus.COMPLETED.value
                        }
                    }

                    openChat.isVisible =
                        status == OrderStatus.ACCEPTED.type
                                || status == OrderStatus.COOKING.type
                                || status == OrderStatus.CREATED.type

                    val address = StringBuilder()
                    cookerAddress?.street?.let {
                        address.append(cookerAddress.street)
                    }
                    cookerAddress?.house?.let {
                        address.append(", ")
                        address.append(cookerAddress.house)
                    }
                    cookerAddress?.flat?.let {
                        address.append(", кв. ")
                        address.append(cookerAddress.flat)
                    }
                    cookerAddress?.block?.let {
                        address.append(", корп. ")
                        address.append(cookerAddress.block)
                    }
                    cookerAddressTv.text = address
                }
            }
        }

        cookerAddressContainer.setDebounceOnClickListener {
            viewModel.onCookerAddressClick()
        }

        openChat.setDebounceOnClickListener {
            viewModel.onOpenChatClick()
        }
    }

    override fun onBackClick() {
        listener?.onCollapseClick()
    }

    private fun initFoodRv() {
        orderDetailRecyclerAdapter = OrderDetailRecyclerAdapter()
        foodsRv.layoutManager = LinearLayoutManager(context)
        foodsRv.adapter = orderDetailRecyclerAdapter
        foodsRv.addItemDecoration(
            RecyclerViewVerticalLineDivider(
                requireContext(),
                R.drawable.bg_divider_recycler
            )
        )
    }

}