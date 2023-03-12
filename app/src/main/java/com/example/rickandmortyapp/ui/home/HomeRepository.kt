package com.example.rickandmortyapp.ui.home

import com.example.rickandmortyapp.base.BaseRepository
import com.example.rickandmortyapp.network.ApiFactory
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getLocation() = safeApiRequest {
        apiFactory.getLocation()
    }
}