package com.example.rickandmortyapp.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentHomeBinding
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.ui.home.adapters.CharacterRecyclerAdapter
import com.example.rickandmortyapp.ui.home.adapters.LocationRecyclerAdapter
import com.example.rickandmortyapp.ui.home.listeners.CharacterClickListener
import com.example.rickandmortyapp.ui.home.listeners.LocationClickListener
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
            val adapter = LocationRecyclerAdapter(object : LocationClickListener {
                override fun onLocationClick(location: Result) {
                      val characterIds = viewModel.selectIds(location.residents)
                      if (characterIds != null) {
                          viewModel.getMultipleCharacters(characterIds,location.residents!!.size)
                      }
                      else{
                          binding.characterRv.adapter = null
                      }
                }
            })
            binding.locationRv.adapter = adapter
            it?.let {
                adapter.setLocations(it.results as List<Result>)
            }
        })

        viewModel.multipleCharacterResponse.observe(viewLifecycleOwner, Observer {
            val adapter = CharacterRecyclerAdapter(object : CharacterClickListener{
                override fun onCharacterClick(character: CharacterResponseItem) {

                }
            })
            binding.characterRv.adapter = adapter
            it?.let {
                adapter.setCharacters(it as ArrayList<CharacterResponseItem>)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })
    }



}