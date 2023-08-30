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

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val composite = CompositeDisposable()

    fun searchRecipes(q: String) {
        recipeRepository.getRecipeListBySearching(q)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .doOnSubscribe { _isLoading.value = true }
            .subscribe(
                {
                    _searchResult.value = it
                    _isLoading.value = searchResult.value?.isEmpty() == true
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage ?: "unknown error in SearchViewModel"
                    )
                }
            )
            .addToComposite(composite)
    }

    fun addFavoriteRecipe(recipe: RecipeData) {
        recipeRepository.addFavoriteRecipe(recipe)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe()
            .addToComposite(composite)
    }

    fun updateIsFavorite(recipe: RecipeData) {
        recipeRepository.updateIsFavorite(recipe)
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe()
            .addToComposite(composite)
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}