package com.example.cookbook.data.mappers

import androidx.core.net.toUri
import com.example.cookbook.data.models.ItemOfRecipeListResponse
import com.example.cookbook.domain.models.RecipeData
import javax.inject.Inject

class ResponseToDataRecipeMapper @Inject constructor() {

    operator fun invoke(response: ItemOfRecipeListResponse) = with(response) {
        RecipeData(
            id = 0,
            label = recipe?.label.orEmpty(),
            image = recipe?.image?.toUri()?.buildUpon()?.scheme("https")?.build().toString(),
            url = recipe?.url?.toUri()?.buildUpon()?.scheme("https")?.build().toString(),
            mealType = recipe?.mealType?.get(0) ?: "",
            ingredientLines = recipe?.ingredientLines.orEmpty(),
            totalTime = getTotalTime(recipe?.totalTime),
            isFavorite = false
        )
    }

    private fun getTotalTime(time: Double?): String {
        val totalTimeInt = time?.toInt() ?: return "-"
        return if (totalTimeInt == 0) {
            "-"
        } else if (totalTimeInt < 60) {
            "${totalTimeInt}m"
        } else if (totalTimeInt % 60 == 0) {
            "${totalTimeInt / 60}h"
        } else "${totalTimeInt / 60}h ${totalTimeInt % 60}m"
    }
}