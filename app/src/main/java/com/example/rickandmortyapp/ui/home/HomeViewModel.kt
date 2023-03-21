package com.example.rickandmortyapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.model.location.LocationResponse
import com.example.rickandmortyapp.network.ApiFactory
import com.example.rickandmortyapp.network.NetworkResult
import com.example.rickandmortyapp.paging.LocationPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val multipleCharacterResponse : MutableLiveData<List<CharacterResponseItem>?> = MutableLiveData()
    val firstLocation : MutableLiveData<List<String>> = MutableLiveData()
    val error : MutableLiveData<String?> = MutableLiveData()


    val listLocation = Pager(PagingConfig(pageSize = 1)){
        LocationPagingSource(repository)
    }.flow.cachedIn(viewModelScope)


    fun getMultipleCharacters(ids: String, size: Int) = viewModelScope.launch {
        when(size){
            1 -> {
                val request = repository.getSingleCharacter(ids)
                when(request){
                    is NetworkResult.Success -> {
                        val list = ArrayList<CharacterResponseItem>()
                        request.data?.let { list.add(it) }
                        multipleCharacterResponse.value = list
                    }
                    else -> {
                        error.value = request.message
                    }
                }
            }
            else -> {
                val request = repository.getMultipleCharacters(ids)
                when(request){
                    is NetworkResult.Success -> {
                        multipleCharacterResponse.value = request.data
                    }
                    else -> {
                        error.value = request.message
                    }
                }
            }
        }
    }

    fun getFirstLocation()  = viewModelScope.launch{
        val request = repository.getFirstLocation()
        when(request){
            is NetworkResult.Success -> {
                firstLocation.value = request.data?.residents
            }
            else -> {
                error.value = request.message
            }
        }
    }

    fun selectIds(residents : List<String>) : String?{
        return repository.selectIds(residents)
    }
}