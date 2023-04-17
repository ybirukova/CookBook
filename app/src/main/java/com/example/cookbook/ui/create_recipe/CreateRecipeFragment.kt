package com.example.cookbook.ui.create_recipe

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cookbook.R
import com.example.cookbook.RecipeApp
import com.example.cookbook.databinding.FragmentCreateRecipeBinding
import com.example.cookbook.di.ViewModelFactory
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.utils.Constants.Companion.EMPTY_STRING
import javax.inject.Inject

class CreateRecipeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: OwnRecipesViewModel by viewModels { factory }

    private var _binding: FragmentCreateRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        (activity?.application as RecipeApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

        setButtonClickListener()
        setEditTextListener(binding.etMin)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

    private fun setIsSuccessful() {
        with(binding) {
            viewModel.setIsSuccessful(
                etTitle.text.toString(),
                etAddProduct.text.toString(),
                etDescription.text.toString()
            )
        }
    }

    private fun setButtonClickListener() {
        binding.buttonDone.setOnClickListener {
            setIsSuccessful()
            viewModel.isSuccessful.observe(viewLifecycleOwner) { isSuccessful ->
                if (isSuccessful) {
                    createAndSaveOwnRecipeData()
                    showSuccessToast()
                    findNavController().popBackStack()
                } else {
                    showErrorToast()
                }
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

    private fun createAndSaveOwnRecipeData() {
        with(binding) {
            val hoursStr =
                if (etHours.text.toString() == EMPTY_STRING) "-" else etHours.text.toString()
            val minStr = if (etMin.text.toString() == EMPTY_STRING) "-" else etMin.text.toString()
            val totalTime =
                "$hoursStr ${resources.getString(R.string.hours)} $minStr ${resources.getString(R.string.min)}"

            viewModel.createAndSaveRecipeData(
                label = etTitle.text.toString(),
                url = etDescription.text.toString(),
                mealType = spinnerMealType.selectedItem.toString(),
                ingredientLines = listOf(etAddProduct.text.toString()),
                totalTime = totalTime
            )
        }
    }

    private fun showSuccessToast() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.create_recipe_success_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setEditTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (input.isNotEmpty() && input.toInt() >= MAX_MIN) {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.impossible_value),
                        Toast.LENGTH_SHORT
                    ).show()
                    editText.setText(EMPTY_STRING)
                }
            }
        })
    }

    companion object {
        const val MAX_MIN = 60
    }
}