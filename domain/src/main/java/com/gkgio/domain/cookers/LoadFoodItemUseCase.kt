package com.gkgio.domain.cookers

import io.reactivex.Single
import javax.inject.Inject

interface LoadFoodItemUseCase {
    fun loadLunch(cookerId: String, lunchId: String): Single<Lunch>
    fun loadMeal(cookerId: String, mealId: String): Single<Meal>
}

class LoadFoodItemUseCaseImpl @Inject constructor(
    private val cookersService: CookersService
) : LoadFoodItemUseCase {
    override fun loadLunch(cookerId: String, lunchId: String): Single<Lunch> =
        cookersService.loadLunch(cookerId, lunchId)

    override fun loadMeal(cookerId: String, mealId: String): Single<Meal> =
        cookersService.loadMeal(cookerId, mealId)
}