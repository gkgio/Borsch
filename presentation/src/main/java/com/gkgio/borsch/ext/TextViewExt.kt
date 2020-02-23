package com.gkgio.borsch.ext

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

fun TextView.setTextOrHide(text: CharSequence?) {
    isVisible = text.notIsNullOrBlank()
    setText(text)
}