package com.example.cookbook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbook.R
import com.example.cookbook.domain.models.RecipeData

abstract class BaseFragment : Fragment() {

    protected var itemClickAction: (RecipeData) -> Unit = { recipe ->
        val bundle = Bundle()
        bundle.putParcelable("RECIPE", recipe)
        findNavController().navigate(R.id.fragmentFullRecipe, bundle)
        (activity as MainActivity).supportActionBar?.hide()
    }
}