package com.gkgio.borsch.cookers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.gkgio.borsch.location.saved.SavedAddressesSheet
import kotlinx.android.synthetic.main.empty_error_view.*
import kotlinx.android.synthetic.main.fragment_cookers.*
import kotlinx.android.synthetic.main.fragment_cookers.emptyErrorView
import kotlinx.android.synthetic.main.fragment_cookers.progress


class CookersFragment : BaseFragment<CookersViewModel>() {

    companion object {
        const val LUNCH_TYPE = 1
        const val MEAL_TYPE = 2

        val TAG = CookersFragment::class.java.simpleName
    }

    private var cookersRecyclerAdapter: CookersRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_cookers

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.cookersViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        closeKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCookersRv()

        addressContainer.setDebounceOnClickListener {
            viewModel.onCurrentAddressContainerClick()
        }

        cookersSwipeToRefreshLayout.setOnRefreshListener {
            viewModel.loadCookers()
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isLoading
            cookersSwipeToRefreshLayout.isRefreshing = state.isLoading
            emptyErrorView.isVisible = state.isInitialError

            addressClientTv.text = state.lastAddedAddress
            addressContainer.isVisible = state.lastAddedAddress != null

            state.cookers?.let {
                cookersRecyclerAdapter?.setCookersList(it)
                noCookersView.isVisible = it.isEmpty()
            }
        }

        viewModel.openAddressesSheet.observeValue(this) {
            showDialog(SavedAddressesSheet(), TAG)
        }

        updateEmptyBtn.setDebounceOnClickListener {
            viewModel.loadCookers()
        }

    }

    private fun initCookersRv() {
        cookersRecyclerAdapter = CookersRecyclerAdapter(
            { cookerId ->
                viewModel.onCookerClick(cookerId)
            },
            { cookerId, foodId, type, cookerAddress ->
                viewModel.onCookerFoodClick(cookerId, foodId, type, cookerAddress)
            })
        cookersRv.adapter = cookersRecyclerAdapter
        cookersRv.layoutManager = LinearLayoutManager(context)
    }
}