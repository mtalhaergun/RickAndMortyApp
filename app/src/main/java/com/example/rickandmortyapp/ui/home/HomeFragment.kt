package com.example.rickandmortyapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentHomeBinding
import com.example.rickandmortyapp.model.location.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLocation()
    }



    override fun onCreateFinished() {

    }

    override fun observeEvents() {
        viewModel.locationResponse.observe(viewLifecycleOwner, Observer {
            val adapter = LocationRecyclerAdapter(object : LocationClickListener{
                override fun onLocationClick(location: Result) {

                }

            })
            binding.locationRv.adapter = adapter
            it?.let {
                adapter.setLocations(it.results as List<Result>)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })
    }

}