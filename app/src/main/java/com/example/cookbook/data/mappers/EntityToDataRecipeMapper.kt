package com.example.cookbook.data.mappers

import com.example.cookbook.data.database.RecipeEntity
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.utils.TypeConverter
import javax.inject.Inject

class EntityToDataRecipeMapper @Inject constructor(private val typeConverter: TypeConverter) {

    operator fun invoke(entity: RecipeEntity) = with(entity) {
        RecipeData(
            label = label,
            image = image,
            url = url,
            mealType = mealType,
            ingredientLines = typeConverter.jsonToSubject(ingredientLines),
            totalTime = totalTime
        )
    }
}