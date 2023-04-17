package com.example.cookbook.ui.create_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import com.example.cookbook.utils.Constants.Companion.ZERO
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnRecipesViewModel @Inject constructor(private val recipeRepository: OwnRecipeRepository) :
    ViewModel() {

    var ownRecipes: LiveData<List<RecipeData>>? = null

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isSuccessful = MutableLiveData(false)
    val isSuccessful: LiveData<Boolean>
        get() = _isSuccessful

    init {
        observeRecipes()
    }

    private fun observeRecipes() {
        _isLoading.value = true
        viewModelScope.launch {
            ownRecipes = recipeRepository.getRecipeListSync()
            _isLoading.value = false
        }
    }

    fun deleteRecipe(id: Int) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(id)
        }
    }

    fun setIsSuccessful(title: String, ingredients: String, description: String) {
        _isSuccessful.value = !(title.isBlank() || ingredients.isBlank() || description.isBlank())
    }

    fun createAndSaveRecipeData(
        id: Int = ZERO,
        label: String,
        image: String = EMPTY_STRING,
        url: String,
        mealType: String = EMPTY_STRING,
        ingredientLines: List<String>,
        totalTime: String = EMPTY_STRING,
        isFavorite: Boolean = false
    ) {
        viewModelScope.launch {
            recipeRepository.addNewRecipe(
                RecipeData(id, label, image, url, mealType, ingredientLines, totalTime, isFavorite)
            )
        }
    }
}