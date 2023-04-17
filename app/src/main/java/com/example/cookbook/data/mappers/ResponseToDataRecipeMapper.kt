package com.example.cookbook.data.mappers

import androidx.core.net.toUri
import com.example.cookbook.data.models.ItemOfRecipeListResponse
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.utils.Constants.Companion.DASH
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import com.example.cookbook.utils.Constants.Companion.ZERO
import java.util.*
import javax.inject.Inject

class ResponseToDataRecipeMapper @Inject constructor() {

    companion object {
        const val HTTPS = "https"
    }

    operator fun invoke(response: ItemOfRecipeListResponse) = with(response) {
        RecipeData(
            id = ZERO,
            label = recipe?.label.orEmpty(),
            image = recipe?.image?.toUri()?.buildUpon()?.scheme(HTTPS)?.build().toString(),
            url = recipe?.url?.toUri()?.buildUpon()?.scheme(HTTPS)?.build().toString(),
            mealType = recipe?.mealType?.get(ZERO) ?: EMPTY_STRING,
            ingredientLines = recipe?.ingredientLines.orEmpty(),
            totalTime = getTotalTime(recipe?.totalTime),
            isFavorite = false
        )
    }

    private fun getTotalTime(time: Double?): String {
        val totalTimeInt = time?.toInt() ?: return EMPTY_STRING
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, ZERO)
        calendar.set(Calendar.MINUTE, totalTimeInt)
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val hoursStr = if (hours == ZERO) DASH else "$hours h "
        val minStr = if (min == ZERO) DASH else "$min min"

        return hoursStr + minStr
    }
}