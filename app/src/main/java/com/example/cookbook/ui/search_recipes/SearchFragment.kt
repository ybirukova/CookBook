package com.example.cookbook.ui.search_recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentSearchBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.BaseFragment
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.recycler.RecipesAdapter
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { factory }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var updateIsFavorite: (RecipeData, Boolean) -> Unit = { recipe, isChecked ->
        val newRecipe = recipe.copy(isFavorite = isChecked)
        viewModel.addFavoriteRecipe(newRecipe)
    }

    private val searchAdapter = RecipesAdapter(itemClickAction, updateIsFavorite)

    override fun onAttach(context: Context) {
        (activity?.application as RecipeApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)


        setItemListeners()
        setupRecyclerView()
        observeAllRecipes()
        observeLoadingStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

    private fun setItemListeners() {
        with(binding) {
            etSearch.requestFocus()
            etSearch.setOnEditorActionListener { textView, actionId, _ ->
                (activity as MainActivity).hideKeyboardFrom(requireContext(), requireView())

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchRecipes(textView.text.toString())
                    return@setOnEditorActionListener true
                }
                false
            }
            ibClose.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvSearchResult) {
            adapter = searchAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
                ?.let()
                { dividerItemDecoration.setDrawable(it) }
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun observeAllRecipes() {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner) {
            it?.let { list ->
                searchAdapter.updateRecipes(list)
            }
        }
    }

    private fun observeLoadingStatus() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            with(binding) {
                tvEnterTextForSearching.isVisible = it
                rvSearchResult.isVisible = !it
            }
        }
    }
}