package com.example.cookbook.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentSearchBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.fragments_menu.BaseFragment
import com.example.cookbook.ui.recycler.RecipesAdapter
import com.example.cookbook.ui.viewmodels.SearchViewModel
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { factory }
    private lateinit var binding: FragmentSearchBinding

    private var updateIsFavorite: (RecipeData, Boolean) -> Unit = { recipe, isChecked ->
        recipe.isFavorite = isChecked
        viewModel.addFavoriteRecipe(recipe)
    }

    private val searchAdapter = RecipesAdapter(itemClickAction, updateIsFavorite)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as RecipeApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)

        setEditTextListener()
        setupRecyclerView()
        observeAllRecipes()
        observeLoadingStatus()
        close()
    }

    private fun close() {
        val closeItem = binding.ibClose
        closeItem.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setEditTextListener() {
        val editTextSearch = binding.etSearch
        editTextSearch.requestFocus()
        editTextSearch.setOnEditorActionListener { textView, actionId, _ ->
            (activity as MainActivity).hideKeyboardFrom(requireContext(), requireView())

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchRecipes(textView.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupRecyclerView() {
        val recycler = binding.rvSearchResult
        recycler.adapter = searchAdapter
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun observeAllRecipes() {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner) {
            it?.let {
                searchAdapter.updateRecipes(it)
            }
        }
    }

    private fun observeLoadingStatus() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.tvEnterTextForSearching.isVisible = it
            binding.rvSearchResult.isVisible = !it
        }
    }
}