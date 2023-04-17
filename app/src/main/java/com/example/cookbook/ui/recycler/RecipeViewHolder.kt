package com.example.cookbook.ui.recycler

import androidx.core.view.isVisible
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
            if (!recipe.url.startsWith("http")) {
                cbLike.isVisible = false
                buttonDelete.isVisible = true
            } else {
                cbLike.isVisible = true
                buttonDelete.isVisible = false
            }

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

            cbLike.setOnClickListener {
                toggleIsFavourite?.invoke(recipe, cbLike.isChecked)
            }

            buttonDelete.setOnClickListener {
                removeRecipe?.invoke(recipe.id)
            }

            itemView.setOnClickListener {
                itemClickAction?.invoke(recipe)
            }
        }
    }
}