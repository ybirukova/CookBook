package com.example.cookbook.data.models

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("label") val label: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("mealType") val mealType: String? = null,
    @SerializedName("ingredientLines") val ingredientLines: List<String>? = null,
    @SerializedName("totalTime") val totalTime: Double? = null
)