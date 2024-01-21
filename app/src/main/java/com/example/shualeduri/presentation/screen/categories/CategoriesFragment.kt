package com.example.shualeduri.presentation.screen.categories

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shualeduri.R
import com.example.shualeduri.presentation.base.BaseFragment
import com.example.shualeduri.databinding.FragmentCategoriesBinding
import com.example.shualeduri.presentation.screen.categories.adapter.CategoriesRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment :
    BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private val categoryRecyclerViewAdapter = CategoriesRecyclerViewAdapter()

    override fun setUp() {
        with(binding.recyclerCategories) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = categoryRecyclerViewAdapter
        }
    }

    override fun setUpListeners() {
        categoryRecyclerViewAdapter.onCategoryClickListener = { category ->
            setFragmentResult(
                "credentialsRequest", bundleOf(
                    "category" to category.categories.category
                )
            )
            findNavController().popBackStack(R.id.profileFragment, false)
            findNavController().navigateUp()
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categories.collect {
                    categoryRecyclerViewAdapter.submitList(it)
                }
            }
        }
    }

}