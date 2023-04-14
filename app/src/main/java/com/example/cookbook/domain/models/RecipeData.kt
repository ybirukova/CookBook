package com.example.cookbook.domain.models

import java.io.Serializable

data class RecipeData(
    val id: Int,
    val label: String,
    val image: String,
    val url: String,
    val mealType: String,
    val ingredientLines: List<String>,
    val totalTime: String,
    var isFavorite: Boolean
) : Serializable