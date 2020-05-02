package com.gkgio.borsch.cookers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.cookers.CookersFragment.Companion.LUNCH_TYPE
import com.gkgio.borsch.cookers.CookersFragment.Companion.MEAL_TYPE
import com.gkgio.borsch.cookers.detail.LunchUi
import com.gkgio.borsch.cookers.detail.MealUi
import com.gkgio.borsch.ext.placeholderByDrawable
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.view.SyntheticViewHolder
import com.gkgio.domain.cookers.Lunch
import com.gkgio.domain.cookers.Meal
import kotlinx.android.synthetic.main.layout_lunch_view_holder.view.*
import kotlinx.android.synthetic.main.layout_meal_view_holder.view.*

class MealsRecyclerAdapter(
    private val mealsList: List<MealUi>,
    private val lunchesList: List<LunchUi>,
    val itemClick: (String, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return when (viewType) {
            LUNCH_TYPE -> LunchViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_lunch_view_holder,
                    parent,
                    false
                )
            )
            MEAL_TYPE -> MealsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_meal_view_holder,
                    parent,
                    false
                )
            )
            else -> MealsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_meal_view_holder,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LunchViewHolder) {
            with(holder.itemView) {
                val lunches = lunchesList[position]

                icActiveLunch.setImageResource(
                    if (lunches.available) {
                        R.drawable.ic_active
                    } else {
                        R.drawable.ic_inactive
                    }
                )
                discountPercentTv.text = String.format("%d%% скидка", lunches.discountPercent)

                val lunchMealsRecyclerAdapter = LunchMealsRecyclerAdapter(lunches.meals) {
                    itemClick(lunches.id, LUNCH_TYPE)
                }
                rvLunchMeals.adapter = lunchMealsRecyclerAdapter
                rvLunchMeals.layoutManager =
                    LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                setDebounceOnClickListener {
                    itemClick(lunches.id, LUNCH_TYPE)
                }
            }
        } else if (holder is MealsViewHolder) {
            with(holder.itemView) {
                val meal = mealsList[position - lunchesList.size]
                Glide.with(mealIv)
                    .load(meal.imageUrl)
                    .placeholderByDrawable(R.drawable.ic_dish_place_holder)
                    .withCenterCropRoundedCorners(context, 18)
                    .into(mealIv)

                icActiveMeal.setImageResource(
                    if (meal.available == true) {
                        R.drawable.ic_active
                    } else {
                        R.drawable.ic_inactive
                    }
                )

                setDebounceOnClickListener {
                    itemClick(meal.id, MEAL_TYPE)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size + lunchesList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position < lunchesList.size) {
            return LUNCH_TYPE
        }

        if (position - lunchesList.size < mealsList.size) {
            return MEAL_TYPE
        }

        return -1
    }

    class MealsViewHolder(view: View) : SyntheticViewHolder(view)

    class LunchViewHolder(view: View) : SyntheticViewHolder(view)
}