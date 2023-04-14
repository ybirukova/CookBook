package com.example.cookbook.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentRecipeBinding
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity


class FullRecipeFragment : Fragment() {

    companion object {
        private val RECIPE = "RECIPE"

        fun newInstance(recipe: RecipeData): FullRecipeFragment {
            val args = Bundle()
            args.putSerializable(RECIPE, recipe)

            val fragment = FullRecipeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
//        (activity as MainActivity).supportActionBar?.hide()
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = binding.ivFrImage
        val title = binding.tvFrTitle
        val mealType = binding.tvFrMealType
        val totalTime = binding.tvFrTotalTime
        val ingredients = binding.tvFrIngredients
        val button = binding.buttonLink

        val recipe = arguments?.getSerializable("RECIPE") as? RecipeData
        if (recipe != null) {
            if (recipe.image.isBlank()) {
                image.setImageResource(R.drawable.ic_default_recipe_pic)
            } else
                Glide
                    .with(view.context)
                    .load(recipe.image)
                    .into(image)
        }

        title.text = recipe?.label
        mealType.text = recipe?.mealType
        totalTime.text = recipe?.totalTime
        "•  ${recipe?.ingredientLines?.joinToString("\n•  ")}".also { ingredients.text = it }

        button.setOnClickListener {
            val uri = Uri.parse(recipe?.url)
            val openLinkIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(openLinkIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        (activity as MainActivity).supportActionBar?.show()
    }
}