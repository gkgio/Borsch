package com.gkgio.borsch.view.brandbutton

import android.content.Context
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.dpToPx
import com.gkgio.borsch.ext.getColorCompat
import com.gkgio.borsch.utils.BackgroundCreator
import com.gkgio.borsch.utils.BaseBrandGradientComponentCreator

class BorschBrandButtonBackgroundCreator(
    private val context: Context,
    private val cornerRadius: Int = DEFAULT_CORNER_RADIUS
) : BackgroundCreator {

    companion object {
        const val DEFAULT_CORNER_RADIUS = 8
    }

    private val brandGradientComponentCreator = BaseBrandGradientComponentCreator(context)

    override fun create() = brandGradientComponentCreator
        .create()
        .cornerRadius(context.dpToPx(cornerRadius))
        .ripple(true)
        .rippleColor(context.getColorCompat(R.color.white))
        .build()
}