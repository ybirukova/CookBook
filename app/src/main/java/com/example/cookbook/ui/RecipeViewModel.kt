package com.example.cookbook.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    private val _recipeLiveData = MutableLiveData<List<RecipeData>>()
    val recipeLiveData: LiveData<List<RecipeData>> get() = _recipeLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val _searchLiveData = MutableLiveData<String>()
    val searchLiveData: MutableLiveData<String> get() = _searchLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadingLiveData.value = false
        when (throwable) {
            else -> {
                _errorLiveData.value = "error"
            }
        }
    }

    fun getRandomRecipeList() {
        viewModelScope.launch(exceptionHandler) {
            delay(2000)
            val list = recipeRepository.getRandomRecipeList()
            _recipeLiveData.value = list
            _loadingLiveData.value = false
        }
    }

    fun searchRecipes(q: String) {
        viewModelScope.launch(exceptionHandler) {
            delay(3000)
            val list = recipeRepository.getRecipeListBySearching(q)
            _recipeLiveData.value = list
            _loadingLiveData.value = false
        }
    }

    fun setSearchWord(str: String) {
        _searchLiveData.value = str
    }
}