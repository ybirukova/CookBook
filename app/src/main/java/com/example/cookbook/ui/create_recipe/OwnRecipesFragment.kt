package com.example.cookbook.ui.create_recipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentOwnRecipesBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.ui.BaseFragment
import com.example.cookbook.ui.recycler.RecipesAdapter
import javax.inject.Inject

class OwnRecipesFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: OwnRecipesViewModel by viewModels { factory }

    private var _binding: FragmentOwnRecipesBinding? = null
    private val binding get() = _binding!!

    private val removeRecipe: (Int) -> Unit = {
        viewModel.deleteRecipe(it)
    }

    private val ownRecipesAdapter = RecipesAdapter(itemClickAction, removeRecipe = removeRecipe)

    override fun onAttach(context: Context) {
        (activity?.application as RecipeApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOwnRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setupRecyclerView()
        observeAllRecipes()
        observeLoadingStatus()
    }

    private fun setOnClickListener() {
        val button = binding.createRecipe
        button.setOnClickListener {
            findNavController().navigate(R.id.fragmentCreateRecipe)
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
        viewModel.ownRecipes.observe(viewLifecycleOwner) {
            it?.let { list ->
                ownRecipesAdapter.updateRecipes(it)
                binding.tvCreateYourOwnRecipe.isVisible = list.isEmpty()
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