package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.model.location.LocationResponse
import retrofit2.http.GET

interface ApiFactory {

    @GET("location")
    suspend fun getLocation() : LocationResponse

}