package com.example.cookbook.ui.fragments_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cookbook.R
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.fragments.FullRecipeFragment

abstract class BaseFragment : Fragment() {

    protected var itemClickAction: (RecipeData) -> Unit = { recipe ->
        val fragment = FullRecipeFragment.newInstance(recipe)
        val bundle = Bundle()
        bundle.putSerializable("RECIPE", recipe)
        fragment.arguments = bundle
        (activity as MainActivity).supportActionBar?.hide()
        openFragment(fragment, true)
    }

    protected fun openFragment(fragment: Fragment, addToBackStack: Boolean) {

        val transaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}