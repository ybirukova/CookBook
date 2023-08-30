package com.example.cookbook.ui.all_recipes

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

class AllRecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("SchedulerIO") private val schedulerIo: Scheduler,
    @Named("SchedulerMainThread") private val schedulerMainThread: Scheduler,
) : ViewModel() {

    private val _allRecipes = MutableLiveData<List<RecipeData>>(listOf())
    val allRecipes: LiveData<List<RecipeData>>
        get() = _allRecipes

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val composite = CompositeDisposable()

    init {
        refreshRecipes()
        observeRecipes()
    }

    private fun refreshRecipes() {
        recipeRepository.refreshDatabaseWithRandomRecipes()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribe()
            .addToComposite(composite)
    }

    private fun observeRecipes() {
        recipeRepository.getRecipeListSync()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .doOnSubscribe { _isLoading.value = true }
            .subscribe(
                {
                    _allRecipes.value = it
                    _isLoading.value = allRecipes.value?.isEmpty() == true
                }, {
                    Log.d(
                        "ERROR_LOG",
                        it.localizedMessage ?: "unknown error in AllRecipesViewModel"
                    )
                }
            )
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