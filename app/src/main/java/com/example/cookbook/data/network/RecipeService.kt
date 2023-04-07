package com.example.cookbook.data.network

import com.example.cookbook.data.models.RecipeListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("v2?app_id=7bddfa9d&app_key=999c6f75e642b7de7aa49d0f12945a03&q=random&type=public&random=true")
    fun getRandomRecipeList(): Call<RecipeListResponse>

    @GET("v2?app_id=7bddfa9d&app_key=999c6f75e642b7de7aa49d0f12945a03&type=public")
    fun getRecipeListBySearching(@Query("q") search: String): Call<RecipeListResponse>

}