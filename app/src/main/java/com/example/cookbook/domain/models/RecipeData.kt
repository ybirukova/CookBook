package com.example.cookbook.domain.models

data class RecipeData(
    val label: String,
    val image: String,
    val url: String,
    val mealType: String,
    val ingredientLines: List<String>,
    val totalTime: String
)