package com.example.cookbook.ui.fragments_menu

import android.os.Bundle
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
import com.example.cookbook.databinding.FragmentFavoritesRecipesBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.recycler.RecipesAdapter
import com.example.cookbook.ui.viewmodels.FavouriteRecipesViewModel
import javax.inject.Inject

class FavoriteRecipesFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: FavouriteRecipesViewModel by viewModels { factory }

    private lateinit var binding: FragmentFavoritesRecipesBinding

    private val updateIsFavorite: (RecipeData, Boolean) -> Unit = { recipe, isChecked ->
        recipe.isFavorite = isChecked
        viewModel.updateIsFavorite(recipe)
    }

    private val favoriteRecipesAdapter = RecipesAdapter(itemClickAction, updateIsFavorite)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesRecipesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)

        setupRecyclerView()
        observeFavoriteRecipes()
        observeLoadingStatus()
    }

    private fun setupRecyclerView() {
        val recycler = binding.favoritesRecipesRv
        recycler.adapter = favoriteRecipesAdapter
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun observeFavoriteRecipes() {
        viewModel.favoriteRecipes?.observe(viewLifecycleOwner) {
            it?.let { list ->
                favoriteRecipesAdapter.updateRecipes(list)
                binding.placeholderText.isVisible = list.isEmpty()
            }
        }
    }

    private fun observeLoadingStatus() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.favoritesRecipesRv.isVisible = !it
        }
    }
}