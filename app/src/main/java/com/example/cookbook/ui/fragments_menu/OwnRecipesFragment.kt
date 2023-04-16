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
import com.example.cookbook.databinding.FragmentOwnRecipesBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.ui.fragments.CreateRecipeFragment
import com.example.cookbook.ui.recycler.RecipesAdapter
import com.example.cookbook.ui.viewmodels.OwnRecipesViewModel
import javax.inject.Inject

class OwnRecipesFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: OwnRecipesViewModel by viewModels { factory }

    lateinit var binding: FragmentOwnRecipesBinding

    private val ownRecipesAdapter = RecipesAdapter(itemClickAction, null)

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
        (activity?.application as RecipeApp).appComponent.inject(this)

        setOnClickListener()
        setupRecyclerView()
        observeAllRecipes()
        observeLoadingStatus()
    }

    private fun setOnClickListener() {
        val button = binding.createRecipe
        button.setOnClickListener {
            openFragment(CreateRecipeFragment(), true)
        }
    }

    private fun setupRecyclerView() {
        val recycler = binding.rvOwnRecipes
        recycler.adapter = ownRecipesAdapter
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun observeAllRecipes() {
        viewModel.ownRecipes?.observe(viewLifecycleOwner) {
            it?.let {
                ownRecipesAdapter.updateRecipes(it)
            }
        }
    }

    private fun observeLoadingStatus() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.tvCreateYourOwnRecipe.isVisible = it
            binding.rvOwnRecipes.isVisible = !it
        }
    }
}