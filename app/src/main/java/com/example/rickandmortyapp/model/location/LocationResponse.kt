package com.example.rickandmortyapp.model.location


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: İnfo?,
    @SerializedName("results")
    val results: List<Result?>?
)