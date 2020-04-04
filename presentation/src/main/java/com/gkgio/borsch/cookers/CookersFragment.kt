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
import kotlinx.android.synthetic.main.fragment_cookers.*


class CookersFragment : BaseFragment<CookersViewModel>() {

    companion object {
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

        viewModel.state.observeValue(this) { state ->
            addressClientTv.text = state.lastAddedAddress
            addressContainer.isVisible = state.lastAddedAddress != null

            state.cookers?.let {
                cookersRecyclerAdapter?.setCookersList(it)
            }
        }

        viewModel.openAddressesSheet.observeValue(this) {
            showDialog(SavedAddressesSheet(), TAG)
        }
    }

    private fun initCookersRv() {
        cookersRecyclerAdapter = CookersRecyclerAdapter(
            { cookerId ->
                viewModel.onCookerClick(cookerId)
            },
            { id, type ->

            })
        cookersRv.adapter = cookersRecyclerAdapter
        cookersRv.layoutManager = LinearLayoutManager(context)
    }

    private fun initFiltersRv() {
        filtersRv
    }
}