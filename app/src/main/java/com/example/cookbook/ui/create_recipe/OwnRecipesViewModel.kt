package com.example.cookbook.ui.create_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookbook.data.scheduler_provider.SchedulerProvider
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import com.example.cookbook.utils.Constants.Companion.ZERO
import com.example.cookbook.utils.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OwnRecipesViewModel @Inject constructor(
    private val recipeRepository: OwnRecipeRepository,
    private val schedulerProvider: SchedulerProvider
) :
    ViewModel() {

    private val _ownRecipes = MutableLiveData<List<RecipeData>>(listOf())
    val ownRecipes: LiveData<List<RecipeData>>
        get() = _ownRecipes

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
        recipeRepository.getRecipeListSync()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .subscribe({
                _ownRecipes.value = it
                _isLoading.value = ownRecipes.value?.isEmpty() == true
            }, {
                it.localizedMessage
            })
            .addToComposite(composite)
    }

    fun deleteRecipe(id: Int) {
        recipeRepository.deleteRecipe(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe()
            .addToComposite(composite)

        observeRecipes()
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
        recipeRepository.addNewRecipe(
            RecipeData(id, label, image, url, mealType, ingredientLines, totalTime, isFavorite)
        )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe()
            .addToComposite(composite)
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}