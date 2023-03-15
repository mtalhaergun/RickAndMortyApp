package com.example.rickandmortyapp.ui.home

import com.example.rickandmortyapp.base.BaseRepository
import com.example.rickandmortyapp.network.ApiFactory
import com.example.rickandmortyapp.utils.Constants.DELIMITER
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getLocation() = safeApiRequest {
        apiFactory.getLocation()
    }

    suspend fun getMultipleCharacters(ids : String) = safeApiRequest {
        apiFactory.getMultipleCharacters(ids)
    }

    suspend fun getSingleCharacter(id : String) = safeApiRequest {
        apiFactory.getCharacter(id)
    }

    fun selectIds(residents : List<String?>?) : String?{
        var idList: List<String>?
        var ids : String? = null
        if (residents != null) {
            for(item in residents){
                if (item != null) {
                    idList = item.split(DELIMITER)
                    if(ids == null){
                        ids = idList[1]
                    }else{
                        ids = ids + "," + idList[1]
                    }
                }
            }
        }
        println(ids)
        return ids
    }
}