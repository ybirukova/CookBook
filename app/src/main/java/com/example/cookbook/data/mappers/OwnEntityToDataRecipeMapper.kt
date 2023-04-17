package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.OwnRecipeEntity
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import javax.inject.Inject

class OwnEntityToDataRecipeMapper @Inject constructor() {

    operator fun invoke(entity: OwnRecipeEntity) = with(entity) {
        RecipeData(
            id = id,
            label = title,
            image = EMPTY_STRING,
            url = description,
            mealType = mealType,
            ingredientLines = listOf(*ingredients.split("\n").toTypedArray()),
            totalTime = totalTime,
            isFavorite = false
        )
    }
}