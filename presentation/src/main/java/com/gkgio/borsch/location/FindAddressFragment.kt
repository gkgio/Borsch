package com.gkgio.borsch.location

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.closeKeyboard
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.openKeyBoard
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_find_address.*

class FindAddressFragment : BaseFragment<FindAddressViewModel>() {

    companion object {
        fun newInstance(isOpenFromOnboarding: Boolean) = FindAddressFragment().apply {
            this.isOpenFromOnboarding = isOpenFromOnboarding
        }
    }

    private var isOpenFromOnboarding: Boolean by FragmentArgumentDelegate()

    private var findAddressRecyclerAdapter: FindAddressRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_find_address

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.findAddressViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddressesRv()

        addressSearchView.setSearchViewChangeListener {
            viewModel.onSearchTextChanged(it)
        }

        addressSearchView.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        viewModel.closeKeyBoard.observeValue(this) {
            closeKeyboard()
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isProgress
            state.geoSuggestionList?.let {
                findAddressRecyclerAdapter?.setGeoSuggestionList(it)
            }
        }
    }

    private fun initAddressesRv() {
        findAddressRecyclerAdapter = FindAddressRecyclerAdapter {
            if (it.data.streetWithType == null || it.data.house == null) {
                addressSearchView.currentInput = it.value
                openKeyBoard()
            } else {
                viewModel.onAddressSelectClick(it, isOpenFromOnboarding)
            }
        }
        rvAddresses.layoutManager = LinearLayoutManager(context)
        rvAddresses.adapter = findAddressRecyclerAdapter
    }

    override fun onStop() {
        super.onStop()
        closeKeyboard()
    }
}