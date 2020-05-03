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
        private const val ARG_IS_INSIDE_PAGE = "isInsidePage"
        val TAG = CookersFragment::class.java.simpleName

        fun newInstance(isInsidePage: Boolean = false) = BasketFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_IS_INSIDE_PAGE, isInsidePage)
            }
        }
    }

    private var isInsidePage: Boolean? = null

    private var basketItemRecyclerAdapter: BasketItemRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_basket

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.basketViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isInsidePage = it.getBoolean(ARG_IS_INSIDE_PAGE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBasketRv()

        if (isInsidePage == true) {
            toolbar.setLeftIcon(context?.getDrawableCompat(R.drawable.ic_back))
            toolbar.setLeftIconClickListener {
                viewModel.onBackClick()
            }
        }

        findCookerButton.setDebounceOnClickListener {
            viewModel.onFindCookerButtonClick()
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isLoading

            basketEmptyView.isVisible = state.basketDataList.isNullOrEmpty()
            contentContainer.isVisible = !state.basketDataList.isNullOrEmpty()

            cookerAddressTitle.isVisible = state.basketCountAndSumUi?.cookerAddressUi != null
            cookerAddressContainer.isVisible = state.basketCountAndSumUi?.cookerAddressUi != null
            addressTv.text = String.format(
                "%s, %s",
                state.basketCountAndSumUi?.cookerAddressUi?.street,
                state.basketCountAndSumUi?.cookerAddressUi?.house
            )

            cookerAddressContainer.setDebounceOnClickListener {
                viewModel.onCookerAddressClick()
            }

            state.basketDataList?.let {
                basketItemRecyclerAdapter?.setBasketDataList(it)
            }

            sumContainer.isVisible = state.basketCountAndSumUi != null
            sumTv.text = state.basketCountAndSumUi?.sum
        }

        confirmButton.setDebounceOnClickListener {
            viewModel.onOrderConfirmBtnClick(isInsidePage)
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