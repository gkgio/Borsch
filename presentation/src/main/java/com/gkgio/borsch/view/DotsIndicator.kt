package com.gkgio.borsch.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.gkgio.borsch.R

import kotlin.math.roundToInt

class DotsIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_INDICATOR_OFFSET_VALUE = 20
    }

    private lateinit var layoutIndicator: LinearLayout
    private lateinit var textIndicator: TextView
    private lateinit var drawableActive: Drawable
    private lateinit var drawableInactive: Drawable

    private var count: Int = 0

    init {
        init(context)
    }

    private fun init(context: Context) {
        layoutIndicator = LinearLayout(context)
        layoutIndicator.orientation = LinearLayout.HORIZONTAL

        textIndicator = AppCompatTextView(context)
        textIndicator.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_text))

        addView(
            layoutIndicator,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            )
        )
        addView(
            textIndicator,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
        )

        drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_dot_disabled)!!
        drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_dot_enabled)!!
    }

    fun setCount(itemsCount: Int) {
        this.count = itemsCount
        invalidateIndicators()
    }

    fun setIndicatorItem(index: Int) {
        if (hasSeparator()) {
            textIndicator.text = context.getString(R.string.dots_separator, index + 1, count)
        } else {
            for (position in 0 until layoutIndicator.childCount) {
                val imageView = layoutIndicator.getChildAt(position) as ImageView
                imageView.setImageDrawable(if (position == index) drawableActive else drawableInactive)
            }
        }
    }

    private fun hasSeparator() = count > DEFAULT_INDICATOR_OFFSET_VALUE

    private fun invalidateIndicators() {
        val hasSeparator = hasSeparator()
        textIndicator.isInvisible = !hasSeparator
        layoutIndicator.isInvisible = hasSeparator

        layoutIndicator.removeAllViews()
        if (!hasSeparator) {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val margin = context.resources
                .getDimension(R.dimen.dots_indicator_dot_margin).roundToInt()

            params.setMargins(margin, 0, margin, 0)
            for (i in 0 until count) {
                val imageView = ImageView(context)
                imageView.setImageDrawable(drawableInactive)
                layoutIndicator.addView(imageView, params)
            }
        }
        setIndicatorItem(0)
    }
}