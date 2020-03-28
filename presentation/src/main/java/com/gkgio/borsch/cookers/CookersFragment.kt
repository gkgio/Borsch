package com.gkgio.borsch.cookers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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

    override fun getLayoutId(): Int = R.layout.fragment_cookers

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.cookersViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressContainer.setDebounceOnClickListener {
            viewModel.onCurrentAddressContainerClick()
        }

        viewModel.state.observeValue(this) { state ->
            addressClientTv.text = state.lastAddedAddress
            addressContainer.isVisible = state.lastAddedAddress != null
        }

        viewModel.openAddressesSheet.observeValue(this) {
            showDialog(SavedAddressesSheet(), TAG)
        }
    }
}