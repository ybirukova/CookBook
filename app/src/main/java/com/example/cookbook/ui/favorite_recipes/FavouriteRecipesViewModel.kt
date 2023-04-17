package com.example.cookbook.ui.favorite_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteRecipesViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    var favoriteRecipes: LiveData<List<RecipeData>>? = null

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        observeFavouriteRecipes()
    }

    private fun observeFavouriteRecipes() {
        _isLoading.value = true
        viewModelScope.launch {
            favoriteRecipes = recipeRepository.getFavoriteRecipeListSync()
            _isLoading.value = false
        }
    }

    fun updateIsFavorite(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.updateIsFavorite(recipe)
        }
    }
}