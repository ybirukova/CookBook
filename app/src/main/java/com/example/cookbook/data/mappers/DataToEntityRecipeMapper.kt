package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.RecipeEntity
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.utils.TypeConverter
import javax.inject.Inject

class DataToEntityRecipeMapper @Inject constructor(private val typeConverter: TypeConverter) {

    operator fun invoke(data: RecipeData) = with(data) {
        RecipeEntity(
            label = label,
            image = image,
            url = url,
            mealType = mealType,
            ingredientLines = typeConverter.subToJson(ingredientLines),
            totalTime = totalTime,
            isFavorite = isFavorite,
            isOwnRecipe = isOwnRecipe
        )
    }
}