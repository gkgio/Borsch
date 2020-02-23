package com.gkgio.borsch.view

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.forEach
import com.gkgio.borsch.ext.getDrawableCompat

open class RecyclerViewVerticalLineDivider(
    context: Context,
    resId: Int
) : RecyclerView.ItemDecoration() {

    private var divider = context.getDrawableCompat(resId)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        parent.forEach {
            val params = it.layoutParams as RecyclerView.LayoutParams

            val top = it.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            with(divider) {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }
    }
}