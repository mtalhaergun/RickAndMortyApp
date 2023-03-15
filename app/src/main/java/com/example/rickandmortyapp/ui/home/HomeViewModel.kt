package com.example.rickandmortyapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.character.CharacterResponse
import com.example.rickandmortyapp.model.location.LocationResponse
import com.example.rickandmortyapp.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val locationResponse : MutableLiveData<LocationResponse?> = MutableLiveData()
    val characterResponse : MutableLiveData<CharacterResponse?> = MutableLiveData()
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

    fun getCharacters(ids : String) = viewModelScope.launch {
        val request = repository.getCharacters(ids)
        when(request){
            is NetworkResult.Success -> {
                characterResponse.value = request.data
            }
            else -> {
                error.value = request.message
            }
        }
    }

    fun selectIds(residents : List<String?>?) : String?{
        return repository.selectIds(residents)
    }
}