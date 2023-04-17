package com.example.cookbook.data.models

import com.google.gson.annotations.SerializedName

data class ItemOfRecipeListResponse(
    @SerializedName("recipe") val recipe: RecipeResponse? = null
)