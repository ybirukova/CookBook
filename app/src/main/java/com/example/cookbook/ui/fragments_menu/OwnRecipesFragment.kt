package com.example.cookbook.ui.fragments_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentOwnRecipesBinding
import com.example.cookbook.ui.fragments.CreateRecipeFragment

class OwnRecipesFragment : Fragment() {

    lateinit var binding: FragmentOwnRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOwnRecipesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = binding.createRecipe
        button.setOnClickListener {
            openFragment(CreateRecipeFragment(), true)
        }
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean) {

        val transaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}