package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.OwnRecipeEntity
import com.example.cookbook.domain.models.RecipeData
import javax.inject.Inject

class OwnEntityToDataRecipeMapper @Inject constructor() {

    operator fun invoke(entity: OwnRecipeEntity) = with(entity) {
        RecipeData(
            id = id,
            label = title,
            image = "",
            url = description,
            mealType = mealType,
            ingredientLines = listOf(ingredients),
            totalTime = totalTime,
            isFavorite = false
        )
    }
}