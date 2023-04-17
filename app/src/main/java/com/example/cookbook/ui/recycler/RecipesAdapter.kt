package com.example.cookbook.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.RecipeItemBinding
import com.example.cookbook.domain.models.RecipeData

class RecipesAdapter(
    private val itemClickAction: ((RecipeData) -> Unit),
    private val toggleIsFavourite: ((RecipeData, Boolean) -> Unit)? = null,
    private val removeRecipe: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<RecipeViewHolder>() {

    private var recipesList: MutableList<RecipeData> = mutableListOf()

    fun updateRecipes(recipes: List<RecipeData>) {
        recipesList.clear()
        recipesList.addAll(recipes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, itemClickAction, toggleIsFavourite, removeRecipe)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.onBind(recipesList[position])
    }

    override fun getItemCount(): Int = recipesList.size
}