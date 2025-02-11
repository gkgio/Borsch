package com.gkgio.borsch.cookers.detail.meals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
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
import com.gkgio.borsch.ext.setTextOrHide
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_lunch_vertical_view_holder.view.*
import kotlinx.android.synthetic.main.layout_meal_vertical_view_holder.view.*
import java.math.BigDecimal

class MealsVerticalRecyclerAdapter(
    private val mealsList: List<MealUi>,
    private val lunchesList: List<LunchUi>,
    val itemClick: (String, Int) -> Unit,
    val addToBasketClick: (String, String, String?, BigDecimal, Int, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return when (viewType) {
            LUNCH_TYPE -> LunchViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_lunch_vertical_view_holder,
                    parent,
                    false
                )
            )
            MEAL_TYPE -> MealsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_meal_vertical_view_holder,
                    parent,
                    false
                )
            )
            else -> MealsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_meal_vertical_view_holder,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LunchViewHolder) {
            with(holder.itemView) {
                val lunch = lunchesList[position]
                val lunchMealsRecyclerAdapter = LunchMealsVerticalRecyclerAdapter(lunch.meals) {
                    itemClick(lunch.id, LUNCH_TYPE)
                }
                rvLunchMeals.adapter = lunchMealsRecyclerAdapter
                rvLunchMeals.layoutManager =
                    LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                lunchNameTv.text = lunch.name
                lunchPriceTv.text = lunch.price
                portionWeightTv.setTextOrHide(lunch.weight)

                setDebounceOnClickListener {
                    itemClick(lunch.id, LUNCH_TYPE)
                }

                notAvailableLunchContainer.isVisible = !lunch.available || lunch.portions == 0
                addToBasketLunchIv.isInvisible = !lunch.available || lunch.portions == 0
                addToBasketLunchIv.setDebounceOnClickListener {
                    addToBasketClick(
                        lunch.id,
                        lunch.name,
                        lunch.imageUrl,
                        lunch.pricePure,
                        lunch.portions,
                        LUNCH_TYPE
                    )
                }
            }
        } else if (holder is MealsViewHolder) {
            with(holder.itemView) {
                val meal = mealsList[position - lunchesList.size]
                Glide.with(mealIv)
                    .load(meal.imageUrl)
                    .placeholderByDrawable(R.drawable.ic_dish_place_holder)
                    .withCenterCropRoundedCorners(context, 8)
                    .into(mealIv)

                mealNameTv.text = meal.name
                mealPriceTv.text = meal.price
                mealPortionTv.setTextOrHide(meal.weight)

                setDebounceOnClickListener {
                    itemClick(meal.id, MEAL_TYPE)
                }

                notAvailableMealContainer.isVisible = meal.available == false || meal.portions == 0
                addToBasketMealIv.isInvisible = meal.available == false || meal.portions == 0
                addToBasketMealIv.setDebounceOnClickListener {
                    addToBasketClick(
                        meal.id,
                        meal.name,
                        meal.imageUrl,
                        meal.purePrice,
                        meal.portions,
                        MEAL_TYPE
                    )
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