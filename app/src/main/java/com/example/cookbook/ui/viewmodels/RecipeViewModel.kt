package com.example.cookbook.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    private val _randomRecipeLiveData = MutableLiveData<List<RecipeData>>()
    val randomRecipeLiveData: LiveData<List<RecipeData>> get() = _randomRecipeLiveData

    private val _favoriteRecipeLiveData = MutableLiveData<List<RecipeData>>()
    val favoriteRecipeLiveData: LiveData<List<RecipeData>> get() = _favoriteRecipeLiveData

    private val _searchResultLiveData = MutableLiveData<List<RecipeData>>()
    val searchResultLiveData: LiveData<List<RecipeData>> get() = _searchResultLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _searchLiveData = MutableLiveData<String>()
    val searchLiveData: MutableLiveData<String> get() = _searchLiveData


    init {
        refreshRandomRecipes()
        observeRandomRecipes()
    }

    private fun refreshRandomRecipes() {
        viewModelScope.launch {
            recipeRepository.refreshDatabaseWithRandomRecipes()
        }
    }

    private fun observeRandomRecipes() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            delay(3000)
            _randomRecipeLiveData.value = recipeRepository.getRandomRecipeList()
            _loadingLiveData.value = false
        }
    }

    fun observeFavoriteRecipes() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            delay(1000)
            _favoriteRecipeLiveData.value = recipeRepository.getFavoriteRecipeList()
            _loadingLiveData.value = false
        }
    }

    fun searchRecipes(q: String) {
        viewModelScope.launch {
            delay(3000)
            val list = recipeRepository.getRecipeListBySearching(q)
            _searchResultLiveData.value = list
            _loadingLiveData.value = false
        }
    }

    fun setSearchWord(str: String) {
        _searchLiveData.value = str
    }

    fun updateIsFavorite(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.updateIsFavorite(recipe)
            _favoriteRecipeLiveData.value = recipeRepository.getFavoriteRecipeList()
        }
    }
}