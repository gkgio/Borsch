package com.gkgio.borsch.auth

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.closeKeyboard
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import com.gkgio.borsch.view.sms.SmsCodeCompleteWatcher
import kotlinx.android.synthetic.main.empty_error_view.*
import kotlinx.android.synthetic.main.fragment_validate_phone.*

class ValidatePhoneFragment : BaseFragment<ValidatePhoneViewModel>() {

    companion object {
        fun newInstance(tmpToken: String, phone: String) = ValidatePhoneFragment().apply {
            this.tmpToken = tmpToken
            this.phone = phone
        }
    }

    private var tmpToken: String by FragmentArgumentDelegate()
    private var phone: String by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.fragment_validate_phone

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.validatePhoneViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(tmpToken, phone)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.state.observeValue(this) {
            progress.isVisible = it.isProgress
            emptyView.isVisible = it.isInitialError
        }

        updateEmptyBtn.setDebounceOnClickListener {
            viewModel.onUpdateAfterErrorClick()
        }

        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        viewModel.countDownSmsTimer.observeValue(this) {
            if (it == 0L) {
                timeAgainRequestTitle.isInvisible = true
                validateSmsBtn.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.btn_color)
                timerText.text = getString(R.string.auth_send_sms_again_btn)
            } else {
                timeAgainRequestTitle.isInvisible = false
                validateSmsBtn.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.grey_btn)
                timerText.text = it.toString()
            }
        }

        validateSmsBtn.setDebounceOnClickListener {
            if (timeAgainRequestTitle.isInvisible) {
                viewModel.onResendSmsClick()
                smsCodeView.cleanInputCode()
            }
        }

        smsCodeView.requestFirstNumberFocus()

        smsCodeView.addCodeCompleteWatcher(object :
            SmsCodeCompleteWatcher {
            override fun codeCompleteChanged(complete: Boolean) {
                if (complete) {
                    closeKeyboard()
                    viewModel.onSmsCodeFullInput(smsCodeView.getCode())
                }
            }
        })
    }

}