package com.example.cookbook.ui.fragments_menu

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.ui.recycler.RecipeAdapter

class MainFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.rvRandomRecipes

        viewModel.recipeLiveData.observe(viewLifecycleOwner) { rec ->
            val adapter = RecipeAdapter(rec, itemClick, checkboxClick)
            recycler.adapter = adapter
            recycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}