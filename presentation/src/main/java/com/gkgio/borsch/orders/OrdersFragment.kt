package com.gkgio.borsch.orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : BaseFragment<OrdersViewModel>() {

    private var orderRecyclerAdapter: OrderRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ordersViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrderRv()

        swipeToRefreshLayout.setOnRefreshListener {
            viewModel.loadOrderData()
        }

        viewModel.state.observeValue(this) { state ->
            swipeToRefreshLayout.isRefreshing = state.isLoading
            ordersEmptyView.isVisible = state.orderDataList.isNullOrEmpty()

            state.orderDataList?.let {
                orderRecyclerAdapter?.setOrdersList(it)
            }
        }
    }

    private fun initOrderRv() {
        orderRecyclerAdapter = OrderRecyclerAdapter {

        }
        rvOrders.adapter = orderRecyclerAdapter
        rvOrders.layoutManager = LinearLayoutManager(context)
    }
}