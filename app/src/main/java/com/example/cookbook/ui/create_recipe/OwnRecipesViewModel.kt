package com.example.cookbook.ui.create_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import com.example.cookbook.utils.Constants.Companion.ZERO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OwnRecipesViewModel @Inject constructor(private val recipeRepository: OwnRecipeRepository) :
    ViewModel() {

    val ownRecipes: MutableLiveData<List<RecipeData>> = MutableLiveData()

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isSuccessful = MutableLiveData(false)
    val isSuccessful: LiveData<Boolean>
        get() = _isSuccessful

    private val composite = CompositeDisposable()

    init {
        observeRecipes()
    }

    private fun observeRecipes() {
        _isLoading.value = true

        val disposable = recipeRepository.getRecipeListSync()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                ownRecipes.postValue(it)
                _isLoading.value = false
            }

        composite.add(disposable)
    }

    fun deleteRecipe(id: Int) {
        val disposable = recipeRepository.deleteRecipe(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        composite.add(disposable)
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
        val disposable = recipeRepository.addNewRecipe(
            RecipeData(id, label, image, url, mealType, ingredientLines, totalTime, isFavorite)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        composite.add(disposable)
    }
}