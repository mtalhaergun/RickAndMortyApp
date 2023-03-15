package com.example.rickandmortyapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.RecyclerCharacterLayoutBinding
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.ui.home.listeners.CharacterClickListener

class CharacterRecyclerAdapter (private val listener : CharacterClickListener) : RecyclerView.Adapter<CharacterRecyclerAdapter.CharacterVH>()  {

    private var characters = emptyList<CharacterResponseItem>()

    class CharacterVH(private val binding : RecyclerCharacterLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: CharacterClickListener, character : CharacterResponseItem){
            binding.character = character
            binding.characterClickListener = listener
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCharacterLayoutBinding.inflate(layoutInflater,parent,false)
        return CharacterVH(binding)
    }

    override fun onBindViewHolder(holder: CharacterVH, position: Int) {
        holder.bind(listener,characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun setCharacters(list : ArrayList<CharacterResponseItem>){
        characters = list
        notifyDataSetChanged()
    }
}