package com.gkgio.borsch.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.getDrawableCompat
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import com.gkgio.borsch.utils.FragmentNullableArgumentDelegate
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : BaseFragment<SettingsViewModel>() {

    companion object {
        private const val ARG_IS_INSIDE_PAGE = "isInsidePage"

        fun newInstance(isInsidePage: Boolean = false) = SettingsFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_IS_INSIDE_PAGE, isInsidePage)
            }
        }
    }

    private var isInsidePage: Boolean? = null

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.settingsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isInsidePage = it.getBoolean(ARG_IS_INSIDE_PAGE)
        }
        viewModel.init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isInsidePage == true) {
            toolbar.setLeftIcon(context?.getDrawableCompat(R.drawable.ic_back))
            toolbar.setLeftIconClickListener {
                viewModel.onBackClick()
            }
        }

        authBtn.setDebounceOnClickListener {
            viewModel.onAuthBtnClick()
        }

        exitBtn.setDebounceOnClickListener {
            viewModel.onExitBtnClick()
        }

        viewModel.state.observeValue(this) { state ->
            notAuthContainer.isVisible = state.user == null
            contentContainer.isVisible = state.user != null

            phoneTv.text = state.user?.phone
        }
    }
}