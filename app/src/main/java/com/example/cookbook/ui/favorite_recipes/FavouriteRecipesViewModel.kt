package com.example.cookbook.ui.favorite_recipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import com.example.cookbook.utils.addToComposite
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class FavouriteRecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("SchedulerIO") private val schedulerIo: Scheduler,
    @Named("SchedulerMainThread") private val schedulerMainThread: Scheduler,
) :
    ViewModel() {

    private val _favoriteRecipes = MutableLiveData<List<RecipeData>>(listOf())
    val favoriteRecipes: LiveData<List<RecipeData>>
        get() = _favoriteRecipes

    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean>
        get() = _isLoadingState

    private val composite = CompositeDisposable()

    init {
        observeFavouriteRecipes()
    }

    private fun observeFavouriteRecipes() {
        recipeRepository.getFavoriteRecipeListSync()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .doOnSubscribe { _isLoadingState.value = true }
            .subscribe(
                { recipes ->
                    _favoriteRecipes.value = recipes
                    _isLoadingState.value = false
                    Log.d("SUCCESS_LOG", "fun observeFavouriteRecipes() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage ?: "unknown error in FavouriteRecipesViewModel"
                    )
                }
            )
            .addToComposite(composite)
    }

    fun updateIsFavorite(recipe: RecipeData) {
        recipeRepository.updateIsFavorite(recipe)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe(
                {
                    Log.d("SUCCESS_LOG", "fun updateIsFavorite() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage
                            ?: "unknown error in FavouriteRecipesViewModel fun updateIsFavorite()"
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