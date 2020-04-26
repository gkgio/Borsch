package com.gkgio.borsch.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
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

        editNameBtn.setDebounceOnClickListener {
            if (editNameBtn.text == getString(R.string.profile_change_name)) {
                editNameBtn.text = getString(R.string.profile_save_name)
                nameTv.isVisible = false
                inputNameEt.isVisible = true
                inputNameEt.requestFocus()
                openKeyBoard()
            } else {
                if (inputNameEt.text.isNotBlank()) {
                    viewModel.onNameChanged(inputNameEt.text.toString())
                }
                nameTv.isVisible = true
                inputNameEt.isVisible = false
                editNameBtn.text = getString(R.string.profile_change_name)
                closeKeyboard(inputNameEt)
            }
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isLoading

            contentContainer.isVisible = state.user != null
            notAuthContainer.isVisible = state.user == null

            phoneTv.text = state.user?.phone
            state.user?.firstName?.let {
                nameTv.text = it
                nameTv.setTextColor(requireContext().getColorCompat(R.color.black))
            }
        }

        clientMarketContainer.setDebounceOnClickListener {
            viewModel.onOpenMarketClientClick()
        }
    }

    override fun onStop() {
        super.onStop()
        closeKeyboard(inputNameEt)
    }
}