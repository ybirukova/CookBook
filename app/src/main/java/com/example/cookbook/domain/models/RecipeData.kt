package com.example.cookbook.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeData(
    val id: Int,
    val label: String,
    val image: String,
    val url: String,
    val mealType: String,
    val ingredientLines: List<String>,
    val totalTime: String,
    val isFavorite: Boolean
) : Parcelable