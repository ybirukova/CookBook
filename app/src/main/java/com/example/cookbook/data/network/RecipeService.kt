package com.example.cookbook.data.network

import com.example.cookbook.data.models.RecipeListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET(RANDOM_RECIPE_LIST_REQUEST)
    fun getRandomRecipeList(): Single<RecipeListResponse>

    @GET(RECIPE_LIST_BY_SEARCHING_REQUEST)
    fun getRecipeListBySearching(@Query("q") search: String): Single<RecipeListResponse>

    companion object {
        private const val RANDOM_RECIPE_LIST_REQUEST =
            "v2?app_id=7bddfa9d&app_key=999c6f75e642b7de7aa49d0f12945a03&q=random&type=public&random=true"
        private const val RECIPE_LIST_BY_SEARCHING_REQUEST =
            "v2?app_id=7bddfa9d&app_key=999c6f75e642b7de7aa49d0f12945a03&q=random&type=public&random=true"
    }
}