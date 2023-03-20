package com.example.rickandmortyapp.ui.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.RecyclerLocationLayoutBinding
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.ui.home.listeners.LocationClickListener

class LocationRecyclerAdapter(
    private val listener : LocationClickListener
    ) : PagingDataAdapter<Result,LocationRecyclerAdapter.LocationVH>(diffCallback) {


    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }

    class LocationVH(private val binding : RecyclerLocationLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(listener : LocationClickListener, location : Result){
            binding.locationClickListener = listener
            binding.location = location
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerLocationLayoutBinding.inflate(layoutInflater,parent,false)
        return LocationVH(binding)
    }


    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(listener,currentItem)
        }
    }

}