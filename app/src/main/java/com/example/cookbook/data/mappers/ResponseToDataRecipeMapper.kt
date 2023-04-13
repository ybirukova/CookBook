package com.example.cookbook.data.mappers

import com.example.cookbook.data.models.ItemOfRecipeListResponse
import com.example.cookbook.domain.models.RecipeData
import javax.inject.Inject

class ResponseToDataRecipeMapper @Inject constructor() {

    operator fun invoke(response: ItemOfRecipeListResponse) = with(response) {
        RecipeData(
            label = recipe?.label.orEmpty(),
            image = recipe?.image.orEmpty(),
            url = recipe?.url.orEmpty(),
            mealType = recipe?.mealType?.get(0) ?: "",
            ingredientLines = recipe?.ingredientLines.orEmpty(),
            totalTime = getTotalTime(recipe?.totalTime)
        )
    }

    private fun getTotalTime(time: Double?): String {
        val totalTimeInt = time?.toInt() ?: return "-"
        return if (totalTimeInt == 0) {
            "-"
        } else if (totalTimeInt < 60) {
            "$totalTimeInt m"
        } else if (totalTimeInt % 60 == 0) {
            "${totalTimeInt / 60} h"
        } else "${totalTimeInt / 60} h ${totalTimeInt % 60} m"

//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.MINUTE, totalTimeInt)
//
//        return "${calendar.get(Calendar.HOUR_OF_DAY)} h ${calendar.get(Calendar.MINUTE)} m"
    }
}