package com.example.shualeduri.presentation.screen.wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.shualeduri.presentation.model.category.Categories
import com.example.shualeduri.domain.usecase.wallpapers.GetWallpapersByFilterUseCase
import com.example.shualeduri.domain.usecase.wallpapers.GetWallpapersUseCase
import com.example.shualeduri.presentation.event.WallpapersEvent
import com.example.shualeduri.presentation.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val getWallpapersUseCase: GetWallpapersUseCase,
    private val getWallpapersByFilterUseCase: GetWallpapersByFilterUseCase
) : ViewModel() {

    private var _pagingDataFlow: MutableSharedFlow<PagingData<Image>> = MutableSharedFlow()
    val pagingDataFlow = _pagingDataFlow.cachedIn(viewModelScope)

    init {
        fetchUsersData()
    }

    fun onEvent(event: WallpapersEvent) {
        when (event) {
            is WallpapersEvent.FetchDefaultDataEvent -> fetchUsersData()
            is WallpapersEvent.FilterByQueryEvent -> filterByQuery(query = event.query)
            is WallpapersEvent.FilterByCategoryEvent -> filterByCategory(category = event.category)
        }
    }

    private fun fetchUsersData() {
        viewModelScope.launch {
            getWallpapersUseCase().collectLatest {
                _pagingDataFlow.emit(it)
            }
        }
    }

    private fun filterByCategory(category: String) {
        viewModelScope.launch {
            getWallpapersByFilterUseCase(query = "", category = category).collectLatest {
                _pagingDataFlow.emit(it)
            }
        }
    }

    private fun filterByQuery(query: String) {
        viewModelScope.launch {
            getWallpapersByFilterUseCase(query = query, category = Categories.ALL.category).collectLatest {
                _pagingDataFlow.emit(it)
            }
        }
    }
}