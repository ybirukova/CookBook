package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.OwnRecipeEntity
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import com.example.cookbook.utils.Constants.Companion.ZERO
import javax.inject.Inject

class DataToOwnEntityRecipeMapper @Inject constructor() {

    operator fun invoke(data: RecipeData) = with(data) {
        OwnRecipeEntity(
            title = label,
            description = url,
            mealType = mealType,
            ingredients = if (ingredientLines.isEmpty()) EMPTY_STRING else ingredientLines[ZERO],
            totalTime = totalTime,
            isOwnRecipe = isOwnRecipe
        )
    }
}