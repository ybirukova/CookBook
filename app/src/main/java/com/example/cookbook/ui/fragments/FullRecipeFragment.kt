package com.example.cookbook.ui.fragments

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
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentRecipeBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.viewmodels.SearchViewModel
import java.io.Serializable
import javax.inject.Inject

class FullRecipeFragment : Fragment() {

    companion object {
        private const val RECIPE = "RECIPE"

        fun newInstance(recipe: Serializable): FullRecipeFragment {
            val args = Bundle()
            args.putSerializable(RECIPE, recipe)

            val fragment = FullRecipeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: SearchViewModel by viewModels { factory }

    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)

        val recipe = arguments?.getSerializable("RECIPE") as? RecipeData

        if (recipe != null) {
            setItems(view, recipe)
            setClickListeners(recipe)
        }
    }

    private fun setItems(view: View, recipe: RecipeData) {
        val title = binding.tvFrTitle
        val mealType = binding.tvFrMealType
        val totalTime = binding.tvFrTotalTime
        val ingredients = binding.tvFrIngredients
        val image = binding.ivFrImage
        val checkbox = binding.cbLike
        val button = binding.buttonLink

        checkbox.isChecked = recipe.isFavorite

        if (!recipe.url.startsWith("http")) {
            button.isVisible = false
            checkbox.isVisible = false
        }

        if (recipe.image.isBlank()) {
            image.setImageResource(R.drawable.ic_default_recipe_pic)
        } else
            Glide
                .with(view.context)
                .load(recipe.image)
                .into(image)

        title.text = recipe.label
        mealType.text = recipe.mealType
        totalTime.text = recipe.totalTime
        "•  ${recipe.ingredientLines.joinToString("\n•  ")}".also { ingredients.text = it }

    }

    private fun setClickListeners(recipe: RecipeData) {
        binding.cbLike.setOnCheckedChangeListener { _, isChecked ->
            recipe.isFavorite = isChecked
            viewModel.updateIsFavorite(recipe)
        }

        binding.buttonLink.setOnClickListener {
            val uri = Uri.parse(recipe.url)
            val openLinkIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(openLinkIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }
}