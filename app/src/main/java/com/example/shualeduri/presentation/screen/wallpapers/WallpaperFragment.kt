package com.example.shualeduri.presentation.screen.wallpapers

import android.util.Log.d
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shualeduri.data.common.AppError
import com.example.shualeduri.presentation.model.category.Categories
import com.example.shualeduri.databinding.FragmentWallpaperBinding
import com.example.shualeduri.presentation.base.BaseFragment
import com.example.shualeduri.presentation.event.WallpapersEvent
import com.example.shualeduri.presentation.extension.showSnackBar
import com.example.shualeduri.presentation.model.Image
import com.example.shualeduri.presentation.screen.wallpapers.adapter.WallpapersRecyclerViewAdapter
import com.example.shualeduri.presentation.screen.wallpapers.listener.OnWallpaperClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WallpaperFragment :
    BaseFragment<FragmentWallpaperBinding>(FragmentWallpaperBinding::inflate),
    OnWallpaperClickListener {

    private val viewModel: WallpaperViewModel by viewModels()
    private val wallpaperRecyclerViewAdapter = WallpapersRecyclerViewAdapter()

    override fun setUp() {
        d("showResult", "hello")
        getCategory()
        with(binding.recyclerWallpapers) {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            adapter = wallpaperRecyclerViewAdapter.apply {
                setOnWallpaperClickListener(this@WallpaperFragment)
            }
        }
    }

    private fun searchData(): String {
        return binding.etSearch.text.toString().replace("\\s+".toRegex(), "+")
    }

    override fun setUpListeners() {
        binding.btnSearch.setOnClickListener {
            wallpaperRecyclerViewAdapter.refresh()
            viewModel.onEvent(WallpapersEvent.FilterByQueryEvent(searchData()))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow.collectLatest { pagingData ->
                    wallpaperRecyclerViewAdapter.submitData(pagingData)
                }
            }
        }
        handlePagingState()
    }

    private fun handlePagingState() {
        wallpaperRecyclerViewAdapter.addLoadStateListener { loadStates ->
            when (loadStates.refresh) {
                is LoadState.Error -> {
                    val error: AppError = AppError.fromException((loadStates.refresh as LoadState.Error).error)
                    binding.progressBarWallpaper.visibility = View.GONE
                    binding.root.showSnackBar(error.message)
                }

                else -> {
                    binding.progressBarWallpaper.isVisible = loadStates.refresh is LoadState.Loading
                }
            }
        }
    }


    override fun onWallpaperClick(image: Image) {
        findNavController().navigate(
            WallpaperFragmentDirections.actionWallpaperFragmentToWallpaperDetailsFragment(
                imageId = image.id
            )
        )
    }

    private fun getCategory() {
        setFragmentResultListener("credentialsRequest") { _, result ->
            val category = result.getString("category") ?: Categories.ALL.category
            wallpaperRecyclerViewAdapter.refresh()
            viewModel.onEvent(WallpapersEvent.FilterByCategoryEvent(category))
        }
    }
}