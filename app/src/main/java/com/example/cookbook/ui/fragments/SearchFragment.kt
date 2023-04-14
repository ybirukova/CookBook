package com.example.cookbook.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentSearchBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.viewmodels.RecipeViewModel
import com.example.cookbook.ui.recycler.RecipeAdapter
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: RecipeViewModel by viewModels { factory }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    var itemClick: (RecipeData) -> Unit = { recipe ->
    }

    private var checkboxClick: (RecipeData, Boolean) -> Unit = {_,_ ->}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as RecipeApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextSearch = binding.etSearch
        val recycler = binding.rvSearchResult

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                viewModel.setSearchWord(input)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != "") {
                    val input = s.toString()
                    viewModel.setSearchWord(input)
                }
            }
        })

        viewModel.searchLiveData.observe(viewLifecycleOwner) { searchText ->
            viewModel.searchRecipes(searchText)
        }

        viewModel.searchResultLiveData.observe(viewLifecycleOwner) {
            val adapter = RecipeAdapter(it, itemClick, checkboxClick)
            recycler.adapter = adapter
            recycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}