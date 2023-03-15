package com.example.rickandmortyapp.ui.home.listeners

import com.example.rickandmortyapp.model.character.CharacterResponseItem

interface CharacterClickListener {

    fun onCharacterClick(character : CharacterResponseItem)
}