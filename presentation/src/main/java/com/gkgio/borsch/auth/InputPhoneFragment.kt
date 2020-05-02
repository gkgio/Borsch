package com.gkgio.borsch.auth

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.View
import androidx.core.content.ContextCompat
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import kotlinx.android.synthetic.main.fragment_input_phone.*

class InputPhoneFragment : BaseFragment<InputPhoneViewModel>() {

    companion object {
        private const val ACCEPTED_SYMBOLS = "1234567890+-() "
        private const val PHONE_FORMAT = "+7 ([000]) [000]-[00]-[00]"
        private const val RU_CODE = "+7"
    }

    var inputPhone: String? = null

    override fun getLayoutId(): Int = R.layout.fragment_input_phone

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.inputPhoneViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPhoneView()

        sendCodeBtn.setDebounceOnClickListener {
            viewModel.onSendCodeBtnClick(inputPhone)
        }
    }

    private fun initPhoneView() {
        phoneEditText.inputType = InputType.TYPE_CLASS_NUMBER
        phoneEditText.keyListener = DigitsKeyListener.getInstance(ACCEPTED_SYMBOLS)

        MaskedTextChangedListener.installOn(
            phoneEditText,
            PHONE_FORMAT,
            listOf(PHONE_FORMAT),
            AffinityCalculationStrategy.WHOLE_STRING,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    if (maskFilled) {
                        sendCodeBtn.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.btn_color)
                        sendCodeBtn.isClickable = true
                        inputPhone = RU_CODE + extractedValue
                    } else {
                        sendCodeBtn.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.btn_color_40)
                        sendCodeBtn.isClickable = false
                        inputPhone = null
                    }
                }
            }
        )

        phoneEditText.requestFocus()
        openKeyBoard()
    }

    override fun onStop() {
        super.onStop()
        closeKeyboard(phoneEditText)
    }

}