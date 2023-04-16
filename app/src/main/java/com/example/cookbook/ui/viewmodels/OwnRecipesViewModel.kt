package com.example.cookbook.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnRecipesViewModel @Inject constructor(private val recipeRepository: OwnRecipeRepository) :
    ViewModel() {

    var ownRecipes: LiveData<List<RecipeData>>? = null

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

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

    fun addRecipe(recipe: RecipeData){
        viewModelScope.launch {
            recipeRepository.addNewRecipe(recipe)
        }
    }

    fun deleteRecipe(id: Int){
        viewModelScope.launch {
            recipeRepository.deleteRecipe(id)
        }
    }
}