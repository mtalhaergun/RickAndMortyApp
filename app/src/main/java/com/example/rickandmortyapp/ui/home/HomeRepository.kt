package com.example.rickandmortyapp.ui.home

import com.example.rickandmortyapp.base.BaseRepository
import com.example.rickandmortyapp.network.ApiFactory
import com.example.rickandmortyapp.utils.Constants.DELIMITERCHARACTER
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getLocation(page : Int) = safeApiRequest{
        apiFactory.getLocation(page)
    }

    suspend fun getMultipleCharacters(ids : String) = safeApiRequest {
        apiFactory.getMultipleCharacters(ids)
    }

    suspend fun getSingleCharacter(id : String) = safeApiRequest {
        apiFactory.getCharacter(id)
    }

    suspend fun getFirstLocation() = safeApiRequest {
        apiFactory.getFirstLocation()
    }

    fun selectIds(residents : List<String>) : String?{
        var idList: List<String>?
        var ids : String? = null
        for(item in residents){
            idList = item.split(DELIMITERCHARACTER)
            if(ids == null){
                ids = idList[1]
            }else{
                ids = ids + "," + idList[1]
            }
        }
        return ids
    }
}