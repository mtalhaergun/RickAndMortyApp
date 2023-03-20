package com.example.rickandmortyapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.network.ApiFactory
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.network.NetworkResult
import com.example.rickandmortyapp.ui.home.HomeRepository


class LocationPagingSource(private val repository: HomeRepository) : PagingSource<Int,Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getLocation(currentPage)
            val data = response.data?.results ?: emptyList()
            val responseData = mutableListOf<Result>()
            responseData.addAll(data)
            
            LoadResult.Page(
                data = responseData,
                prevKey = if(currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}




