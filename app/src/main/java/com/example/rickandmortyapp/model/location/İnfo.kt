package com.example.rickandmortyapp.model.location


import com.google.gson.annotations.SerializedName

data class İnfo(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("prev")
    val prev: Any?
)