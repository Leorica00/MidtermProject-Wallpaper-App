package com.example.shualeduri.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shualeduri.BuildConfig
import com.example.shualeduri.data.mapper.wallpapers.toDomain
import com.example.shualeduri.data.service.WallpaperApiService
import com.example.shualeduri.domain.model.GetImage

class WallpaperPagingSource(
    private val apiService: WallpaperApiService,
    private val apiKey: String,
    private val query: String,
    private val category: String
) : PagingSource<Int, GetImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetImage> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getImages(
                apiKey = apiKey, query = query, category = category,
                page = page, perPage = BuildConfig.PAGE_SIZE
            )
            LoadResult.Page(
                data = response.body()!!.toDomain().hits,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()!!.hits.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetImage>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}