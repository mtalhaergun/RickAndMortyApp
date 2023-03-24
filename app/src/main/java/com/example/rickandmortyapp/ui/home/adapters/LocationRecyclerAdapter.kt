package com.example.rickandmortyapp.ui.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.RecyclerLocationLayoutBinding
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.ui.home.listeners.LocationClickListener

class LocationRecyclerAdapter(
    private val listener : LocationClickListener
    ) : PagingDataAdapter<Result,LocationRecyclerAdapter.LocationVH>(diffCallback) {

    var selectedPosition : Int = 0

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


        fun onClick(position: Int, selectedPosition: Int,listener : LocationClickListener, location : Result){
            if (selectedPosition == position) {
                binding.locationCard.strokeColor = Color.parseColor("#FF018786")
                binding.locationCard.setCardBackgroundColor(Color.parseColor("#B2E850"))
                binding.locationName.setTextColor(Color.parseColor("#FF018786"))

            } else {
                binding.locationCard.strokeColor = Color.parseColor("#B2E850")
                binding.locationCard.setCardBackgroundColor(Color.parseColor("#FF018786"))
                binding.locationName.setTextColor(Color.parseColor("#B2E850"))

            }
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

            holder.onClick(position,selectedPosition,listener,currentItem)
        }

        holder.itemView.setOnClickListener {
            selectedPosition = if (position == selectedPosition) {
                -1
            } else {
                position
            }
            notifyDataSetChanged()
            if (currentItem != null) {
                listener.onLocationClick(currentItem)
            }
        }
    }
}