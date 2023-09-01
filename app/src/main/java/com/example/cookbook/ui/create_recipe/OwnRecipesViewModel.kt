package com.example.cookbook.ui.create_recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import com.example.cookbook.utils.Constants.Companion.ZERO
import com.example.cookbook.utils.addToComposite
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class OwnRecipesViewModel @Inject constructor(
    private val recipeRepository: OwnRecipeRepository,
    @Named("SchedulerIO") private val schedulerIo: Scheduler,
    @Named("SchedulerMainThread") private val schedulerMainThread: Scheduler,
) :
    ViewModel() {

    private val _ownRecipes = MutableLiveData<List<RecipeData>>(listOf())
    val ownRecipes: LiveData<List<RecipeData>>
        get() = _ownRecipes

    private val _isShowingMessage = MutableLiveData(true)
    val isShowingMessage: LiveData<Boolean>
        get() = _isShowingMessage

    private val _isSuccessful = MutableLiveData(false)
    val isSuccessful: LiveData<Boolean>
        get() = _isSuccessful

    private val composite = CompositeDisposable()

    init {
        observeRecipes()
    }

    private fun observeRecipes() {
        recipeRepository.getRecipeListSync()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .doOnSubscribe { _isShowingMessage.value = true }
            .subscribe(
                {
                    _ownRecipes.value = it
                    _isShowingMessage.value = ownRecipes.value?.isEmpty()
                    Log.d("SUCCESS_LOG", "fun observeRecipes() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage
                            ?: "unknown error in OwnRecipesViewModel fun observeRecipes()"
                    )
                }
            )
            .addToComposite(composite)
    }

    fun deleteRecipe(id: Int) {
        recipeRepository.deleteRecipe(id)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe(
                {
                    Log.d("SUCCESS_LOG", "fun deleteRecipe() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage
                            ?: "unknown error in OwnRecipesViewModel fun deleteRecipe()"
                    )
                }
            )
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
        isFavorite: Boolean = true,
        isOwnRecipe: Boolean = true
    ) {
        recipeRepository.addNewRecipe(
            RecipeData(
                id,
                label,
                image,
                url,
                mealType,
                ingredientLines,
                totalTime,
                isFavorite,
                isOwnRecipe
            )
        )
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe(
                {
                    Log.d("SUCCESS_LOG", "fun createAndSaveRecipeData() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage
                            ?: "unknown error in OwnRecipesViewModel fun createAndSaveRecipeData()"
                    )
                }
            )
            .addToComposite(composite)
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}