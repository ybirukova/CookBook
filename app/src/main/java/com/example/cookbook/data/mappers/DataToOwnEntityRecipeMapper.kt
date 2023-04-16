package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.OwnRecipeEntity
import com.example.cookbook.domain.models.RecipeData
import javax.inject.Inject

class DataToOwnEntityRecipeMapper @Inject constructor() {

    operator fun invoke(data: RecipeData) = with(data) {
        OwnRecipeEntity(
            title = label,
            description = url,
            mealType = mealType,
            ingredients = ingredientLines[0],
            totalTime = totalTime
        )
    }
}