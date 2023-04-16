package com.example.cookbook.ui.recycler

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.domain.models.RecipeData

class RecipeViewHolder(
    itemView: View,
    private val itemClickAction: ((RecipeData) -> Unit)? = null,
    private val toggleIsFavourite: ((RecipeData, Boolean) -> Unit)? = null
) : RecyclerView.ViewHolder(itemView) {

    fun onBind(recipe: RecipeData) {
        val image = itemView.findViewById<ImageView>(R.id.iv_image_of_dish)
        val title = itemView.findViewById<TextView>(R.id.tv_title_of_recipe)
        val totalTime = itemView.findViewById<TextView>(R.id.tv_total_time)
        val mealType = itemView.findViewById<TextView>(R.id.tv_meal_type)
        val isFavourite = itemView.findViewById<CheckBox>(R.id.cb_like)

        if (!recipe.url.startsWith("http")) {
            isFavourite.isVisible = false
        }
        isFavourite.isChecked = recipe.isFavorite

        title.text = recipe.label
        totalTime.text = recipe.totalTime
        mealType.text = recipe.mealType

        if (recipe.image.isBlank()) {
            image.setImageResource(R.drawable.ic_default_recipe_pic)
        } else
            Glide
                .with(itemView.context)
                .load(recipe.image)
                .into(image)

        isFavourite.setOnClickListener {
            toggleIsFavourite?.invoke(recipe, isFavourite.isChecked)
        }

        itemView.setOnClickListener {
            itemClickAction?.invoke(recipe)
        }
    }
}