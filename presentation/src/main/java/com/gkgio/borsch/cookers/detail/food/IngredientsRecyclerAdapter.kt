package com.gkgio.borsch.cookers.detail.food

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.cookers.detail.MealUi
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_ingredient_view_holder.view.*
import kotlinx.android.synthetic.main.layout_item_food_view_holder.view.*

class IngredientsRecyclerAdapter(
    private val ingredientsList: List<String>
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(
            parent,
            R.layout.layout_ingredient_view_holder
        )
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) {
        with(holder.itemView) {
            val ingredient = ingredientsList[position]
            ingredientTv.text = ingredient
        }
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
}