package com.example.cookbook.ui.search_recipes

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

class SearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("SchedulerIO") private val schedulerIo: Scheduler,
    @Named("SchedulerMainThread") private val schedulerMainThread: Scheduler,
) :
    ViewModel() {

    private val _searchResult = MutableLiveData<List<RecipeData>>()
    val searchResult: LiveData<List<RecipeData>>
        get() = _searchResult

    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean>
        get() = _isLoadingState

    private val composite = CompositeDisposable()

    fun searchRecipes(q: String) {
        recipeRepository.getRecipeListBySearching(q)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .doOnSubscribe { _isLoadingState.value = true }
            .subscribe(
                {
                    _searchResult.value = it
                    _isLoadingState.value = false
                    Log.d("SUCCESS_LOG", "fun searchRecipes() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage
                            ?: "unknown error in SearchViewModel fun searchRecipes()"
                    )
                }
            )
            .addToComposite(composite)
    }

    fun addFavoriteRecipe(recipe: RecipeData) {
        recipeRepository.addFavoriteRecipe(recipe)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe(
                {
                    Log.d("SUCCESS_LOG", "fun addFavoriteRecipe() completed")
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage
                            ?: "unknown error in SearchViewModel fun addFavoriteRecipe()"
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
                            ?: "unknown error in SearchViewModel fun updateIsFavorite()"
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