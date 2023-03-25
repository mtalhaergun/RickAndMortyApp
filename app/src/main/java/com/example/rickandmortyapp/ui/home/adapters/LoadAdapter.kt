package com.example.rickandmortyapp.ui.home.adapters

import android.os.Handler
import android.os.Looper
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
        fun bind(states : LoadState, holder : ViewHolder){
            binding.progressBarLocation.isVisible = states is LoadState.Loading
            if(holder.layoutPosition == 126){
                binding.progressBarLocation.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        binding = LoadItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState,holder)
    }
}