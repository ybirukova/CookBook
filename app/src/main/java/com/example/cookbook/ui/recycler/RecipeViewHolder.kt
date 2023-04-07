package com.example.cookbook.ui.recycler

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.domain.models.RecipeData

class RecipeViewHolder(
    itemView: View,
    private val itemClick: (RecipeData) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    fun onBind(recipe: RecipeData) {
        val image = itemView.findViewById<ImageView>(R.id.iv_image_of_dish)
        val title = itemView.findViewById<TextView>(R.id.tv_title_of_recipe)
        val info = itemView.findViewById<TextView>(R.id.tv_short_info_about_recipe)
        val checkbox = itemView.findViewById<CheckBox>(R.id.cb_like)

        title.text = recipe.label
        //заменить на иконку часы
        info.text = "Time: ${recipe.totalTime}"

        if (recipe.image.isBlank()) {
            image.setImageResource(R.drawable.ic_default_recipe_pic)
        } else
            Glide
                .with(itemView.context)
                .load(recipe.image)
                .into(image)

        itemView.setOnClickListener {
            itemClick.invoke(recipe)
        }
    }
}