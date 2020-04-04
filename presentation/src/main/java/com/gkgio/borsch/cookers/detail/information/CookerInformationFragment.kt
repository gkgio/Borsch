package com.gkgio.borsch.cookers.detail.information

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_cooker_information.*

class CookerInformationFragment : BaseFragment<CookerInformationViewModel>() {

    companion object {
        fun newInstance(description: String) = CookerInformationFragment().apply {
            this.description = description
        }
    }

    var description: String by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.fragment_cooker_information

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.cookerInformationViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(description)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observeValue(this) { state ->
            descriptionTv.text = state.description
        }
    }
}