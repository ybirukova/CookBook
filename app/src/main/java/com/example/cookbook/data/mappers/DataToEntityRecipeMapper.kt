package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.RecipeEntity
import com.example.cookbook.domain.models.RecipeData
import javax.inject.Inject

class DataToEntityRecipeMapper @Inject constructor() {

    operator fun invoke(data: RecipeData) = with(data) {
        RecipeEntity(
            label = label,
            image = image,
            url = url,
            mealType = mealType,
            ingredientLines = ingredientLines,
            totalTime = totalTime
        )
    }
}