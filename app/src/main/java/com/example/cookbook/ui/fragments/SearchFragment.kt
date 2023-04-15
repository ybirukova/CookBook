package com.example.cookbook.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentSearchBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.recycler.RecipeAdapter
import com.example.cookbook.ui.viewmodels.RecipeViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: RecipeViewModel by viewModels { factory }
    private lateinit var binding: FragmentSearchBinding

    var itemClick: (RecipeData) -> Unit = { recipe ->
    }

    private var checkboxClick: (RecipeData, Boolean) -> Unit = { _, _ -> }

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
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)

        val editTextSearch = binding.etSearch
        val recycler = binding.rvSearchResult
        val hint = binding.tvEnterTextForSearching

        editTextSearch.setOnEditorActionListener { textView, actionId, _ ->
            (activity as MainActivity).hideKeyboardFrom(requireContext(), requireView())

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchRecipes(textView.text.toString())
                Log.d("PRINT11", textView.text.toString())

                return@setOnEditorActionListener true
            }
            false
        }

        viewModel.searchResultLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                hint.isVisible = false
                recycler.isVisible = true
                val adapter = RecipeAdapter(it, itemClick, checkboxClick)
                recycler.adapter = adapter
                recycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            } else {
                recycler.isVisible = false
                hint.isVisible = true
            }
        }

        viewModel.searchLiveData.observe(viewLifecycleOwner) { searchText ->
            viewModel.searchRecipes(searchText)
        }
    }
}