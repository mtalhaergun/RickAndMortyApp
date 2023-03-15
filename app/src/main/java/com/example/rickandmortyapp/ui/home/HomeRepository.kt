package com.example.rickandmortyapp.ui.home

import com.example.rickandmortyapp.base.BaseRepository
import com.example.rickandmortyapp.network.ApiFactory
import com.example.rickandmortyapp.utils.Constants.DELIMITER
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getLocation() = safeApiRequest {
        apiFactory.getLocation()
    }

    suspend fun getCharacters(ids : String) = safeApiRequest {
        apiFactory.getCharacters(ids)
    }

    fun selectIds(residents : List<String?>?) : String?{
        var idList: List<String>?
        var ids : String? = null
        if (residents != null) {
            for(item in residents){
                if (item != null) {
                    idList = item.split("character/")
                    if(ids == null){
                        ids = idList[1]
                    }else{
                        ids = ids + "," + idList[1]
                    }
                }
            }
        }else{

        }
        println(ids)
        return ids
    }

}