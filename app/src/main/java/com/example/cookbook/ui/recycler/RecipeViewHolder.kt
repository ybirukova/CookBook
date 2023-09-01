package com.example.cookbook.ui.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.databinding.RecipeItemBinding
import com.example.cookbook.domain.models.RecipeData

class RecipeViewHolder(
    private val binding: RecipeItemBinding,
    private val itemClickAction: ((RecipeData) -> Unit),
    private val toggleIsFavourite: ((RecipeData, Boolean) -> Unit)? = null,
    private val removeRecipe: ((Int) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(recipe: RecipeData) {
        with(binding) {
            cbLike.isChecked = recipe.isFavorite

            tvTitleOfRecipe.text = recipe.label
            tvTotalTime.text = recipe.totalTime
            tvMealType.text = recipe.mealType

            if (recipe.image.isBlank()) {
                ivImageOfDish.setImageResource(R.drawable.ic_default_recipe_pic)
            } else
                Glide
                    .with(itemView.context)
                    .load(recipe.image)
                    .into(ivImageOfDish)

            if (recipe.isOwnRecipe) {
                cbLike.setOnClickListener {
                    removeRecipe?.invoke(recipe.id)
                }
            } else {
                cbLike.setOnClickListener {
                    toggleIsFavourite?.invoke(recipe, cbLike.isChecked)
                }
            }

            itemView.setOnClickListener {
                itemClickAction.invoke(recipe)
            }
        }
    }
}