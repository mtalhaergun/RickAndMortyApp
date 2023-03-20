package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.model.character.CharacterResponse
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.model.location.LocationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiFactory {

    @GET("location")
    suspend fun getLocation(
        @Query("page") page : Int
    ) : LocationResponse

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(
        @Path("ids") ids : String
    ) : ArrayList<CharacterResponseItem>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id : String
    ) : CharacterResponseItem

}