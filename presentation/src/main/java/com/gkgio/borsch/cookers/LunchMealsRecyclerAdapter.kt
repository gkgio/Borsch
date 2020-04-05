package com.gkgio.borsch.cookers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.view.SyntheticViewHolder
import com.gkgio.domain.cookers.Meal
import kotlinx.android.synthetic.main.layout_lunch_meal_view_holder.view.*

class LunchMealsRecyclerAdapter(
    private val mealsList: List<Meal>,
    private val itemClick: () -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_lunch_meal_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) {
        with(holder.itemView) {
            val meal = mealsList[position]

            Glide.with(mealIv)
                .load(meal.imageUrl)
                .withCenterCropRoundedCorners(context, 18)
                .placeholder(R.drawable.ic_dish_place_holder)
                .into(mealIv)

            setDebounceOnClickListener {
                itemClick()
            }
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}