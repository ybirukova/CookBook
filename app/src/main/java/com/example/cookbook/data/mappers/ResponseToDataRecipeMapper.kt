package com.example.cookbook.data.mappers

import com.example.cookbook.data.models.ItemOfRecipeListResponse
import com.example.cookbook.domain.models.RecipeData
import java.util.Calendar
import javax.inject.Inject

class ResponseToDataRecipeMapper @Inject constructor() {

    operator fun invoke(response: ItemOfRecipeListResponse) = with(response) {
        RecipeData(
            label = recipe?.label.orEmpty(),
            image = recipe?.image.orEmpty(),
            url = recipe?.url.orEmpty(),
            mealType = recipe?.mealType.orEmpty(),
            ingredientLines = recipe?.ingredientLines.orEmpty(),
            totalTime = getTotalTime(recipe?.totalTime)
        )
    }

    private fun getTotalTime(time: Double?): String {
        val totalTimeInt = time?.toInt() ?: return "-"
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, totalTimeInt)

        return "${calendar.get(Calendar.HOUR_OF_DAY)} hours $${calendar.get(Calendar.MINUTE)} min"
    }
}