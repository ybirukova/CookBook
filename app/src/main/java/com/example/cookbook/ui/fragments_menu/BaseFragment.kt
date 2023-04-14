package com.example.cookbook.ui.fragments_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentMainBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.fragments.FullRecipeFragment
import com.example.cookbook.ui.fragments.SearchFragment
import com.example.cookbook.ui.viewmodels.RecipeViewModel
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: RecipeViewModel by viewModels { factory }
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    var itemClick: (RecipeData) -> Unit = { recipe ->
        val fragment = FullRecipeFragment.newInstance(recipe)
        val bundle = Bundle()
        bundle.putSerializable("RECIPE", recipe)
        fragment.arguments = bundle
        (activity as MainActivity).supportActionBar?.hide()
        openFragment(fragment, true)
    }

    var checkboxClick: (RecipeData, Boolean) -> Unit = { recipe, isChecked ->
        recipe.isFavorite = isChecked
        viewModel.updateIsFavorite(recipe)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)

        val editTextSearch = binding.etSearchClick
        val recycler = binding.rvRandomRecipes
        val progressBar = binding.progressBar

        editTextSearch.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) { show ->
            progressBar.isVisible = show
            recycler.isVisible = show.not()
        }

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
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