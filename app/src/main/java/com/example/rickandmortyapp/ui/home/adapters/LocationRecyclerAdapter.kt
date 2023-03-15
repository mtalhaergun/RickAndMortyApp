package com.example.rickandmortyapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.RecyclerLocationLayoutBinding
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.ui.home.listeners.LocationClickListener

class LocationRecyclerAdapter(
    private val listener : LocationClickListener
    ) : RecyclerView.Adapter<LocationRecyclerAdapter.LocationVH>() {

    private var locations = emptyList<Result>()

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
        holder.bind(listener,locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setLocations(list : List<Result>){
        locations = list
        notifyDataSetChanged()
    }
}