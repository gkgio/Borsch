package com.gkgio.borsch.cookers.detail.meals

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.cookers.detail.CookerDetailUi
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.utils.ClickDialogCallBack
import com.gkgio.borsch.utils.DialogUtils
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_cooker_meal.*

class CookerMealFragment : BaseFragment<CookerMealViewModel>(), ClickDialogCallBack {

    companion object {
        val TAG_CLEAR_BASKET = "${CookerMealFragment::class.java.simpleName}ClearBasket"

        fun newInstance(cookerDetailUi: CookerDetailUi) = CookerMealFragment().apply {
            this.cookerDetailUi = cookerDetailUi
        }
    }

    lateinit var listener: CookerMealClickListener

    private var cookerDetailUi: CookerDetailUi by FragmentArgumentDelegate()

    private var mealsVerticalRecyclerAdapter: MealsVerticalRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_cooker_meal

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.cookerMealViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = when {
            parentFragment is CookerMealClickListener -> parentFragment as CookerMealClickListener
            context is CookerMealClickListener -> context
            else -> throw IllegalStateException("Either parentFragment or context must implement FoodItemDialogListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()

        viewModel.showClearBasketWarning.observeValue(this) {
            DialogUtils.showDialog(
                TAG_CLEAR_BASKET,
                childFragmentManager,
                "Все ранее выбранные продукты будут удалены из корины. Продолжить?",
                "Да",
                buttonLeftText = "Отмена"
            )
        }
    }

    override fun onRightButtonClick(fragmentTag: String) {
        super.onRightButtonClick(fragmentTag)
        viewModel.addToBasketAfterCleaning(cookerDetailUi.id, cookerDetailUi.cookerAddress)
    }

    private fun initMealsRv() {
        mealsVerticalRecyclerAdapter =
            MealsVerticalRecyclerAdapter(
                cookerDetailUi.meals,
                cookerDetailUi.lunches ?: listOf(),
                { id, type ->
                    listener.onMealClick(id, type)
                },
                { id, name, price ->
                    viewModel.addToBasketClick(
                        id,
                        name,
                        price,
                        cookerDetailUi.id,
                        cookerDetailUi.cookerAddress
                    )
                }
            )
        mealsRv.adapter = mealsVerticalRecyclerAdapter
        mealsRv.layoutManager = LinearLayoutManager(context)
    }
}

interface CookerMealClickListener {
    fun onMealClick(id: String, type: Int)
}