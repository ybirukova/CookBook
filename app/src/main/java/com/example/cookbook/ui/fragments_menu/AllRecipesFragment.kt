package com.example.cookbook.ui.fragments_menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentAllRecipesBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.fragments.SearchFragment
import com.example.cookbook.ui.recycler.RecipesAdapter
import com.example.cookbook.ui.viewmodels.AllRecipesViewModel
import javax.inject.Inject

class AllRecipesFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: AllRecipesViewModel by viewModels { factory }

    private lateinit var binding: FragmentAllRecipesBinding

    private val updateIsFavorite: (RecipeData, Boolean) -> Unit = { recipe, isChecked ->
        recipe.isFavorite = isChecked
        viewModel.updateIsFavorite(recipe)
    }

    private val allRecipesAdapter = RecipesAdapter(itemClickAction, updateIsFavorite)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d("Fragment1", "onCreateView")

        binding = FragmentAllRecipesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)

        setupRecyclerView()
        observeAllRecipes()
        observeLoadingStatus()
        search()
    }

    private fun setupRecyclerView() {
        val recycler = binding.allRecipesRv
        recycler.adapter = allRecipesAdapter
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun observeAllRecipes() {
        viewModel.allRecipes?.observe(viewLifecycleOwner) {
            it?.let {
                allRecipesAdapter.updateRecipes(it)
            }
        }
    }

    private fun observeLoadingStatus() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.allRecipesRv.isVisible = !it
        }
    }

    private fun search() {
        val editTextSearch = binding.etSearchClick
        editTextSearch.setOnClickListener {
            openFragment(SearchFragment(), true)
        }
    }
}