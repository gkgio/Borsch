package com.gkgio.borsch.view.brandbutton

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.dpToPx
import com.gkgio.borsch.ext.getColorCompat
import com.gkgio.borsch.ext.getFont

class BorschBrandButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private companion object {
        private const val LEFT_RIGHT_PADDING_DP = 20
        private const val TOP_BOTTOM_PADDING_DP = 12
        private const val DISABLED_ALPHA = 0.4f
        private const val ENABLED_ALPHA = 1f
    }

    init {
        typeface = context.getFont(R.font.tt_norms_bold)
        gravity = Gravity.CENTER

        applyAttrs(attrs, defStyleAttr)
    }

    private fun applyAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BorschBrandButton,
            defStyleAttr,
            0
        )

        try {
            val leftRightPaddingPx = context.dpToPx(
                typedArray.getInteger(
                    R.styleable.BorschBrandButton_padding_left_right_dp,
                    LEFT_RIGHT_PADDING_DP
                )
            )

            val topBottomPaddingPx = context.dpToPx(
                typedArray.getInteger(
                    R.styleable.BorschBrandButton_padding_top_bottom_dp,
                    TOP_BOTTOM_PADDING_DP
                )
            )

            val cornerRadius = typedArray.getInteger(
                R.styleable.BorschBrandButton_corner_radius,
                BorschBrandButtonBackgroundCreator.DEFAULT_CORNER_RADIUS
            )

            val color = typedArray.getColor(
                R.styleable.BorschBrandButton_color,
                context.getColorCompat(R.color.white)
            )

            setPadding(
                leftRightPaddingPx, topBottomPaddingPx, leftRightPaddingPx, topBottomPaddingPx
            )

            background = BorschBrandButtonBackgroundCreator(context, cornerRadius).create()

            setTextColor(color)
        } finally {
            typedArray.recycle()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        alpha = if (enabled) ENABLED_ALPHA else DISABLED_ALPHA
    }
}