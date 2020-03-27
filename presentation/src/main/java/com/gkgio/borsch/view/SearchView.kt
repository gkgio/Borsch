package com.gkgio.borsch.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.view.isInvisible
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.closeKeyboard
import com.gkgio.borsch.ext.getDrawableCompat
import com.gkgio.borsch.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.layout_search_view.view.*

class SearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var debounceInputRunnable = Runnable { }
    private val callbacksHandler = Handler(Looper.getMainLooper())

    private var leftIconClickListener: (() -> Unit)? = null

    private var debounceInputListener: ((String) -> Unit)? = null
    private val debounceTextChangeListener = object : AbstractTextWatcher() {
        override fun afterTextChanged(text: Editable?) {
            callbacksHandler.removeCallbacks(debounceInputRunnable)
            debounceInputRunnable = Runnable {
                text?.let { debounceInputListener?.invoke(it.toString()) }
            }
            callbacksHandler.postDelayed(debounceInputRunnable, inputDebounce)
        }
    }

    private var nonDebounceInputListener: ((String) -> Unit)? = null
    private val nonDebounceTextChangeListener = object : AbstractTextWatcher() {
        override fun afterTextChanged(text: Editable?) {
            text?.let { nonDebounceInputListener?.invoke(it.toString()) }
        }
    }

    var inputDebounce = 400L

    fun setLeftIconClickListener(clickListener: () -> Unit) {
        leftIconClickListener = clickListener
        iconLeftContainer.setDebounceOnClickListener { leftIconClickListener?.invoke() }
    }

    fun setSearchViewChangeListener(debounceInputListener: ((String) -> Unit)?) {
        this.debounceInputListener = debounceInputListener
        if (debounceInputListener != null) {
            input.addTextChangedListener(debounceTextChangeListener)
        } else {
            input.removeTextChangedListener(debounceTextChangeListener)
        }
    }

    fun setNonDebounceSearchViewChangeListener(inputListener: ((String) -> Unit)?) {
        this.nonDebounceInputListener = inputListener
        if (nonDebounceInputListener != null) {
            input.addTextChangedListener(nonDebounceTextChangeListener)
        } else {
            input.removeTextChangedListener(nonDebounceTextChangeListener)
        }
    }

    var hint: String
        get() = input.hint.toString()
        set(value) {
            input.hint = value
        }

    var currentInput: String
        get() = input.text.toString()
        set(value) {
            input.setText(value)
            input.setSelection(value.length)
            input.requestFocus()
        }

    init {
        inflate(context, R.layout.layout_search_view, this)
        applyAttrs(attrs, defStyleAttr)

        input.addTextChangedListener(object : AbstractTextWatcher() {
            override fun afterTextChanged(text: Editable?) {
                clearBtn.isInvisible = input.text.isNullOrBlank()
            }
        })

        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                closeKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }

        clearBtn.setDebounceOnClickListener { input.text = null }
    }

    private fun applyAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SearchView,
            defStyleAttr,
            0
        )

        try {
            hint = typedArray.getString(R.styleable.SearchView_android_hint)
                ?: context.getString(R.string.default_search_hint)
            val iconLeftRes: Int = typedArray.getResourceId(
                R.styleable.SearchView_leftIcon,
                -1
            )
            if (iconLeftRes > 0) {
                iconLeft.setImageDrawable(context.getDrawableCompat(iconLeftRes))
            }
        } finally {
            typedArray.recycle()
        }
    }
}