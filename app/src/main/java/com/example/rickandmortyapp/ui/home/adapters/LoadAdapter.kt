package com.example.rickandmortyapp.ui.home.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.LoadItemBinding

class LoadAdapter() : LoadStateAdapter<LoadAdapter.ViewHolder>() {

    private lateinit var binding : LoadItemBinding

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun bind(states : LoadState){
            binding.progressBarLocation.isVisible = states is LoadState.Loading
            Log.d("Scroll",states.endOfPaginationReached.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        binding = LoadItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}