package com.example.cookbook.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.domain.models.RecipeData

class RecipesAdapter(
    private val itemClickAction: ((RecipeData) -> Unit)? = null,
    private val toggleIsFavourite: ((RecipeData, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<RecipeViewHolder>() {

    private var recipesList: MutableList<RecipeData> = mutableListOf()

    fun updateRecipes(recipes: List<RecipeData>) {
        recipesList.clear()
        recipesList.addAll(recipes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view, itemClickAction, toggleIsFavourite)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.onBind(recipesList[position])
    }

    override fun getItemCount(): Int = recipesList.size
}