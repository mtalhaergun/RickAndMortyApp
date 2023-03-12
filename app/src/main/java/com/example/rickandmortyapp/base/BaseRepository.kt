package com.example.rickandmortyapp.base

import com.example.rickandmortyapp.network.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiRequest(
        apiRequest : suspend () -> T) : NetworkResult<T>{
        return withContext(Dispatchers.IO){
            try {
                NetworkResult.Success(apiRequest.invoke())
            }catch (throwable : Throwable){
                when(throwable){
                    is HttpException -> {
                        NetworkResult.Error("Request Error!")
                    }
                    else -> NetworkResult.Error(throwable.localizedMessage)
                }
            }
        }
    }
}
