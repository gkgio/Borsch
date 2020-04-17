package com.gkgio.borsch.orders.chat

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel

class OrderChatFragment : BaseFragment<OrderChatViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_order_chat

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.orderChatViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}