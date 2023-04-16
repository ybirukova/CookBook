package com.example.cookbook.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentCreateRecipeBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.viewmodels.OwnRecipesViewModel
import javax.inject.Inject

class CreateRecipeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    val viewModel: OwnRecipesViewModel by viewModels { factory }

    private lateinit var binding: FragmentCreateRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as RecipeApp).appComponent.inject(this)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

        setButtonClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

    private fun setButtonClickListener() {
        val title = binding.etTitle
        val ingredients = binding.etAddProduct
        val description = binding.etDescription
        val button = binding.buttonDone
        button.setOnClickListener {
            if (title.text.toString().isBlank() || ingredients.text.toString()
                    .isBlank() || description.text.toString().isBlank()
            ) {
                showErrorToast()
            } else {
                addRecipe(createOwnRecipeData())
                showSuccessToast()
                close()
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.create_recipe_error_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun createOwnRecipeData(): RecipeData {
        val title = binding.etTitle
        val ingredients = binding.etAddProduct
        val description = binding.etDescription
        val mealTypeSpinner = binding.spinnerMealType
        val hours = binding.etHours
        val min = binding.etMin

        val hoursStr = hours.text.toString()
        val minStr = min.text.toString()

        val totalTime =
            "$hoursStr ${resources.getString(R.string.hours)} $minStr ${resources.getString(R.string.min)}"

        return RecipeData(
            0,
            title.text.toString(),
            "",
            description.text.toString(),
            mealTypeSpinner.selectedItem.toString(),
            listOf(ingredients.text.toString()),
            totalTime,
            false
        )
    }

    private fun addRecipe(recipe: RecipeData) {
        viewModel.addRecipe(recipe)
    }

    private fun showSuccessToast() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.create_recipe_success_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun close() {
        requireActivity().supportFragmentManager.popBackStack()
    }
}