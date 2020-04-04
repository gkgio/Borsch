package com.gkgio.borsch.utils

import android.content.Context
import com.gkgio.borsch.R
import javax.inject.Inject

class WeightFormatter @Inject constructor(private val context: Context) {

    fun format(value: Int): String {
        return context.getString(R.string.weight_format, value)
    }

    fun formatToOnePotion(value: Int):String{
        return context.getString(R.string.weight_1_portion, value)
    }
}