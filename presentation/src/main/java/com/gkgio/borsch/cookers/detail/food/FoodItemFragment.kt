package com.gkgio.borsch.cookers.detail.food

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.cookers.CookersFragment.Companion.LUNCH_TYPE
import com.gkgio.borsch.cookers.detail.FoodItemRequest
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.profile.SettingsViewModel
import com.gkgio.borsch.utils.ClickDialogCallBack
import com.gkgio.borsch.utils.DialogUtils
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.empty_error_view.*
import kotlinx.android.synthetic.main.fragment_food_item.*

interface FoodItemDialogListener {
    fun onCollapseClick()
}

class FoodItemFragment : BaseFragment<FoodItemViewModel>(), ClickDialogCallBack {

    companion object {
        val TAG_DELETE_ITEM = "${FoodItemFragment::class.java.simpleName}DeleteItem"
        val TAG_CLEAR_BASKET = "${FoodItemFragment::class.java.simpleName}ClearBasket"

        fun newInstance(foodItemRequest: FoodItemRequest) = FoodItemFragment().apply {
            this.foodItemRequest = foodItemRequest
        }
    }

    private var foodItemsRecyclerAdapter: FoodItemsRecyclerAdapter? = null

    private var foodItemRequest: FoodItemRequest by FragmentArgumentDelegate()

    private var listener: FoodItemDialogListener? = null

    override fun getLayoutId(): Int = R.layout.fragment_food_item

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.foodItemViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = when {
            parentFragment is FoodItemDialogListener -> parentFragment as FoodItemDialogListener
            context is FoodItemDialogListener -> context
            else -> throw IllegalStateException("Either parentFragment or context must implement FoodItemDialogListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(foodItemRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFoodRv()

        toolbar.setLeftIconClickListener {
            listener?.onCollapseClick()
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isLoading
            emptyErrorView.isVisible = state.isInitialError

            state.lunchUi?.let {
                foodItemsRecyclerAdapter?.setMealsList(it.meals)
            }

            state.mealUi?.let {
                foodItemsRecyclerAdapter?.setMealsList(listOf(it))
            }

            count.text = state.currentCount.toString()
            if (state.goodsWasInBasket) {
                addToBasketButton.text =
                    getString(R.string.basket_add_change_filter, state.currentPriceFormatted)
            } else {
                addToBasketButton.text =
                    getString(R.string.basket_add_filter, state.currentPriceFormatted)
            }
            changeCountContainer.isVisible = state.currentPriceFormatted != null
        }

        viewModel.showClearBasketWarning.observeValue(this) {
            DialogUtils.showDialog(
                TAG_CLEAR_BASKET,
                childFragmentManager,
                "Все ранее выбранные продукты будут удалены из корины. Продолжить?",
                "Да",
                buttonLeftText = "Отмена"
            )
        }

        viewModel.closeDialog.observeValue(this) {
            listener?.onCollapseClick()
        }

        viewModel.showDialogDeleteFromBasket.observeValue(this) {
            DialogUtils.showDialog(
                TAG_DELETE_ITEM,
                childFragmentManager,
                "Удалить из корзины?",
                "Да",
                buttonLeftText = "Отмена"
            )
        }

        updateEmptyBtn.setDebounceOnClickListener {
            viewModel.loadData()
        }

        addToBasketButton.setDebounceOnClickListener {
            viewModel.onAddToBasketClick()
        }

        addBtn.setDebounceOnClickListener {
            viewModel.onPlusCountClick()
        }

        removeBtn.setDebounceOnClickListener {
            viewModel.onMinusCountClick()
        }

    }

    override fun onRightButtonClick(fragmentTag: String) {
        if (fragmentTag == TAG_CLEAR_BASKET) {
            viewModel.onClearBasketClick()
        } else viewModel.onDeleteFromBasketClick()
    }

    override fun onBackClick() {
        listener?.onCollapseClick()
    }

    private fun initFoodRv() {
        foodItemsRecyclerAdapter = FoodItemsRecyclerAdapter()
        foodsRv.adapter = foodItemsRecyclerAdapter
        foodsRv.layoutManager = LinearLayoutManager(context)
    }
}