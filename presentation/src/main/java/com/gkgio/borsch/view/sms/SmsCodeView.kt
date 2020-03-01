package com.gkgio.borsch.view.sms

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.gkgio.borsch.R
import kotlinx.android.synthetic.main.view_sms_code.view.*
import kotlin.properties.Delegates


class SmsCodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var codeLengthWatcher: SmsCodeLengthWatcher? = null
    private var codeCompleteWatcher: SmsCodeCompleteWatcher? = null

    var codeLength: Int by Delegates.observable(
        initialValue = 0,
        onChange = { _, _, newValue ->
            codeLengthWatcher?.codeLengthChanged(newValue)
        }
    )

    var codeComplete: Boolean by Delegates.observable(
        initialValue = false,
        onChange = { _, _, newValue ->
            codeCompleteWatcher?.codeCompleteChanged(newValue)
        }
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.view_sms_code, this, true)

        setMechanic()
    }

    fun addCodeLengthWatcher(watcher: SmsCodeLengthWatcher) {
        codeLengthWatcher = watcher
    }

    fun addCodeCompleteWatcher(watcher: SmsCodeCompleteWatcher) {
        codeCompleteWatcher = watcher
    }

    fun getCode(): String {
        return "${txtNumber1.text}${txtNumber2.text}${txtNumber3.text}${txtNumber4.text}"
    }

    fun setCode(code: String) {
        if (code.length == 4) {
            setCodeFromString(code)
        }
    }

    private fun setCodeFromString(code: String) {
        txtNumber1.setText(code[0].toString())
        txtNumber2.setText(code[1].toString())
        txtNumber3.setText(code[2].toString())
        txtNumber4.setText(code[3].toString())
    }

    private fun updateWatchersValues() {
        codeLength = getCode().length
        codeComplete = getCode().length == 4
    }

    private fun setMechanic() {
        txtNumber1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0.let {
                    if (p0!!.length == 4) {
                        setCodeFromString(p0.toString())
                    } else if (txtNumber1.text.length == 1) {
                        txtNumber1.setSelection(txtNumber1.text.length)
                        if (p0.isNotEmpty()) txtNumber2.requestFocus()
                    } else if (p0.length > 4) {
                        setCode(p0.toString().substring(0, 4))
                    } else if (p0.isNotEmpty()) {
                        txtNumber1.setText(p0.last().toString())
                    }
                }
                updateWatchersValues()
            }

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        txtNumber2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0.let {
                    if (p0!!.length == 4) {
                        setCodeFromString(p0.toString())
                    } else if (txtNumber2.text.length == 1) {
                        txtNumber2.setSelection(txtNumber2.text.length)
                        if (p0.isNotEmpty()) txtNumber3.requestFocus()
                    } else if (p0.length > 4) {
                        setCode(p0.toString().substring(0, 4))
                    } else if (p0.isNotEmpty()) {
                        txtNumber2.setText(p0.last().toString())
                    }
                }
                updateWatchersValues()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        txtNumber3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0.let {
                    if (p0!!.length == 4) {
                        setCodeFromString(p0.toString())
                    } else if (txtNumber3.text.length == 1) {
                        txtNumber3.setSelection(txtNumber3.text.length)
                        if (p0.isNotEmpty()) txtNumber4.requestFocus()
                    } else if (p0.length > 4) {
                        setCode(p0.toString().substring(0, 4))
                    } else if (p0.isNotEmpty()) {
                        txtNumber3.setText(p0.last().toString())
                    }
                }
                updateWatchersValues()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        txtNumber4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0.let {
                    if (p0!!.length == 4) {
                        setCodeFromString(p0.toString())
                    } else if (txtNumber4.text.length == 1) {
                        txtNumber4.setSelection(txtNumber4.text.length)
                    } else if (p0.length > 4) {
                        setCode(p0.toString().substring(0, 4))
                    } else if (p0.isNotEmpty()) {
                        txtNumber4.setText(p0.last().toString())
                    }
                }
                updateWatchersValues()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        txtNumber2.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (txtNumber2.text.isEmpty()) {
                    txtNumber1.setText("")
                    txtNumber1.requestFocus()
                }
            }
            false
        }

        txtNumber3.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (txtNumber3.text.isEmpty()) {
                    txtNumber2.setText("")
                    txtNumber2.requestFocus()
                }
            }
            false
        }

        txtNumber4.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (txtNumber4.text.isEmpty()) {
                    txtNumber3.setText("")
                    txtNumber3.requestFocus()
                }
            }
            false
        }

    }

}