package com.gkgio.borsch.basket

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.cookers.CookersFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.gkgio.borsch.view.RecyclerViewVerticalLineDivider
import kotlinx.android.synthetic.main.fragment_basket.*


class BasketFragment : BaseFragment<BasketViewModel>() {

    companion object {
        val TAG = CookersFragment::class.java.simpleName
    }

    private var basketItemRecyclerAdapter: BasketItemRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_basket

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.basketViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBasketRv()

        viewModel.state.observeValue(this) { state ->
            basketEmptyView.isVisible = state.basketDataList.isNullOrEmpty()

            cookerAddressTitle.isVisible = state.basketCountAndSumUi?.cookerAddressUi != null
            cookerAddressContainer.isVisible = state.basketCountAndSumUi?.cookerAddressUi != null
            addressTv.text = String.format(
                "%s, %s",
                state.basketCountAndSumUi?.cookerAddressUi?.street,
                state.basketCountAndSumUi?.cookerAddressUi?.house
            )

            state.basketDataList?.let {
                basketItemRecyclerAdapter?.setBasketDataList(it)
            }

            sumContainer.isVisible = state.basketCountAndSumUi != null
            sumTv.text = state.basketCountAndSumUi?.sum

        }

        confirmButton.setDebounceOnClickListener {

        }
    }

    private fun initBasketRv() {
        basketItemRecyclerAdapter = BasketItemRecyclerAdapter(
            { basketDataUi, position ->
                viewModel.onPlusBtnClick(basketDataUi, position)
            },
            { basketDataUi, position ->
                viewModel.onMinusBtnClick(basketDataUi, position)
            }
        )
        basketRv.adapter = basketItemRecyclerAdapter
        basketRv.layoutManager = LinearLayoutManager(context)
        basketRv.addItemDecoration(
            RecyclerViewVerticalLineDivider(
                requireContext(),
                R.drawable.bg_divider_recycler
            )
        )
    }
}