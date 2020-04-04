package com.gkgio.borsch.utils

import android.content.Context
import com.gkgio.borsch.R
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

class PriceFormatter @Inject constructor(private val context: Context) {

    private val paymentValueFormat: DecimalFormat

    init {
        val decimalFormatSymbols = DecimalFormatSymbols.getInstance()
        decimalFormatSymbols.decimalSeparator = '.'
        paymentValueFormat = DecimalFormat("0.##")
        paymentValueFormat.decimalFormatSymbols = decimalFormatSymbols
    }

    fun format(value: BigDecimal): String {
        return context.getString(R.string.price_format, paymentValueFormat.format(value))
    }

    fun formatMinPrice(value: BigDecimal): String {
        return context.getString(R.string.min_price_format, paymentValueFormat.format(value))
    }
}
