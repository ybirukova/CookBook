package com.example.cookbook.data.models

import com.google.gson.annotations.SerializedName

data class RecipeListResponse(
    @SerializedName("hits")
    val hits: List<ItemOfRecipeListResponse>? = null
)