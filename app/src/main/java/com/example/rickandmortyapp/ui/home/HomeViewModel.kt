package com.example.rickandmortyapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.model.location.LocationResponse
import com.example.rickandmortyapp.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val locationResponse : MutableLiveData<LocationResponse?> = MutableLiveData()
    val multipleCharacterResponse : MutableLiveData<List<CharacterResponseItem>?> = MutableLiveData()
    val error : MutableLiveData<String?> = MutableLiveData()

    fun getLocation() = viewModelScope.launch {
        val request = repository.getLocation()
        when(request){
            is NetworkResult.Success -> {
                locationResponse.value = request.data
            }
            is NetworkResult.Error -> {
                error.value = request.message
            }
        }
    }

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

    fun selectIds(residents : List<String?>?) : String?{
        return repository.selectIds(residents)
    }
}