package com.gkgio.borsch.cookers.detail.food

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.cookers.CookersFragment.Companion.LUNCH_TYPE
import com.gkgio.borsch.cookers.detail.FoodItemRequest
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.profile.SettingsViewModel
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_food_item.*

interface FoodItemDialogListener {
    fun onCollapseClick()
}

class FoodItemFragment : BaseFragment<FoodItemViewModel>() {

    companion object {
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
        toolbar.setTitle(
            if (foodItemRequest.type == LUNCH_TYPE) getString(R.string.food_combo_lunch_title)
            else getString(R.string.food_meal_title)
        )

        toolbar.setLeftIconClickListener {
            listener?.onCollapseClick()
        }

        viewModel.state.observeValue(this) { state ->

            state.lunchUi?.let {
                foodItemsRecyclerAdapter?.setMealsList(it.meals)
            }

            state.mealUi?.let {
                foodItemsRecyclerAdapter?.setMealsList(listOf(it))
            }
        }
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