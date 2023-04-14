package com.example.cookbook.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.domain.models.RecipeData

class RecipeAdapter(
    private val recipeList: List<RecipeData>,
    private val itemClick: (RecipeData) -> Unit,
    private val checkboxClick: (RecipeData, Boolean) -> Unit
) : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_recipe_item, parent, false)
        return RecipeViewHolder(view, itemClick, checkboxClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.onBind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size
}