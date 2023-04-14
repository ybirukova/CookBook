package com.example.cookbook

import com.example.cookbook.data.mappers.ResponseToDataRecipeMapper
import com.example.cookbook.data.models.ItemOfRecipeListResponse
import com.example.cookbook.data.models.RecipeResponse
import com.example.cookbook.domain.models.RecipeData
import org.junit.Assert
import org.junit.Test

class ResponseToDataMapperTest {
    @Test
    fun mapper_isCorrect() {
        val mapper = ResponseToDataRecipeMapper()
        val response =
            ItemOfRecipeListResponse(
                RecipeResponse(
                    "qqq",
                    "http",
                    "http",
                    listOf("dinner"),
                    listOf("qwe", "asd"),
                    60.0
                )
            )
        val data = RecipeData("qqq", "http", "http", "dinner", listOf("qwe", "asd"), "1 h")
        Assert.assertEquals(data, mapper(response))
    }
}