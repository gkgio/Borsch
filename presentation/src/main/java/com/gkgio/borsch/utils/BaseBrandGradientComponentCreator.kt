package com.gkgio.borsch.utils

import android.content.Context
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.getColorCompat
import top.defaults.drawabletoolbox.DrawableBuilder

class BaseBrandGradientComponentCreator(
    private val context: Context
) : BackgroundComponentCreator {

    override fun create(): DrawableBuilder {
        return DrawableBuilder()
            .rectangle()
            .gradient()
            .gradientColors(
                context.getColorCompat(R.color.blue),
                context.getColorCompat(R.color.btn_color),
                null
            )
            .angle(90)
            .linearGradient()
    }
}