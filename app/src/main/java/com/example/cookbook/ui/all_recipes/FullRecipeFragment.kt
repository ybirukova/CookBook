package com.example.cookbook.ui.all_recipes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentRecipeBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.search_recipes.SearchViewModel
import javax.inject.Inject

class FullRecipeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: SearchViewModel by viewModels { factory }

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        (activity?.application as RecipeApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

        val recipe = arguments?.getParcelable("RECIPE") as? RecipeData
        if (recipe != null) {
            setItems(view, recipe)
            setClickListeners(recipe)
        }
    }

    private fun setItems(view: View, recipe: RecipeData) {
        val title = binding.tvTitle
        val mealType = binding.tvMealType
        val totalTime = binding.tvTotalTime
        val ingredients = binding.tvIngredients
        val image = binding.ivImage
        val checkbox = binding.cbLike
        val button = binding.buttonLink

        checkbox.isChecked = recipe.isFavorite

        if (!recipe.url.startsWith("http")) {
            button.isVisible = false
            checkbox.isVisible = false
        }

        if (recipe.image.isBlank()) {
            image.setImageResource(R.drawable.ic_default_recipe_pic)
        } else {
            Glide
                .with(view.context)
                .load(recipe.image)
                .into(image)
        }

        title.text = recipe.label
        mealType.text = recipe.mealType
        totalTime.text = recipe.totalTime
        "•  ${recipe.ingredientLines.joinToString("\n•  ")}".also { ingredients.text = it }

    }

    private fun setClickListeners(recipe: RecipeData) {
        binding.cbLike.setOnCheckedChangeListener { _, isChecked ->
            val newRecipe = recipe.copy(isFavorite = isChecked)
            viewModel.updateIsFavorite(newRecipe)
        }

        binding.buttonLink.setOnClickListener {
            val uri = Uri.parse(recipe.url)
            val openLinkIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(openLinkIntent)
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }
}