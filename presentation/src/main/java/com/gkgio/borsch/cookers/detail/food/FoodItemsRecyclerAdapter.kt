package com.gkgio.borsch.cookers.detail.food

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.cookers.detail.MealUi
import com.gkgio.borsch.ext.placeholderByDrawable
import com.gkgio.borsch.ext.setTextOrHide
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_item_food_view_holder.view.*

class FoodItemsRecyclerAdapter : RecyclerView.Adapter<SyntheticViewHolder>() {
    private val mealsList = mutableListOf<MealUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(
            parent,
            R.layout.layout_item_food_view_holder
        )
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) {
        with(holder.itemView) {
            val meal = mealsList[position]

            Glide.with(foodIv)
                .load(meal.imageUrl)
                .placeholderByDrawable(R.drawable.ic_dish_place_holder)
                .withCenterCropRoundedCorners(context, 18)
                .into(foodIv)

            mealTitleTv.text = meal.name
            portionWeightTv.setTextOrHide(meal.weight)
            descriptionTv.text = meal.description

            if (!meal.ingredients.isNullOrEmpty()) {
                ingredientsTitleTv.isVisible = true
                ingredientsRv.isVisible = true

                val ingredientsRecyclerAdapter = IngredientsRecyclerAdapter(meal.ingredients)
                ingredientsRv.adapter = ingredientsRecyclerAdapter
                ingredientsRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            } else {
                ingredientsTitleTv.isVisible = false
                ingredientsRv.isVisible = false
            }
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    fun setMealsList(mealsList: List<MealUi>) {
        this.mealsList.clear()
        this.mealsList.addAll(mealsList)
        notifyDataSetChanged()
    }
}