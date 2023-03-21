package com.example.rickandmortyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentHomeBinding
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.ui.home.adapters.CharacterRecyclerAdapter
import com.example.rickandmortyapp.ui.home.adapters.LocationRecyclerAdapter
import com.example.rickandmortyapp.ui.home.listeners.CharacterClickListener
import com.example.rickandmortyapp.ui.home.listeners.LocationClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var firstLocation : List<String> = emptyList()
    private var firstOpen : Boolean = true
    private lateinit var adapterLocation: LocationRecyclerAdapter
    private lateinit var binding : FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.getLocation()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRv()
        loadData()
        observeEvents()
    }

    fun observeEvents() {

        viewModel.multipleCharacterResponse.observe(viewLifecycleOwner, Observer {
            val adapter = CharacterRecyclerAdapter(object : CharacterClickListener{
                override fun onCharacterClick(character: CharacterResponseItem) {
                    val navigation = HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
                    Navigation.findNavController(requireView()).navigate(navigation)
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

        viewModel.firstLocation.observe(viewLifecycleOwner, Observer {
            if(firstOpen){
                firstOpen = false
                val ids = viewModel.selectIds(it)
                if (ids != null) {
                    viewModel.getMultipleCharacters(ids, it.size)
                }
            }
        })
    }

    private fun createRv(){
        adapterLocation = LocationRecyclerAdapter(object : LocationClickListener{
            override fun onLocationClick(location: Result) {
                    val characterIds = viewModel.selectIds(location.residents)
                    if (characterIds != null) {
                        viewModel.getMultipleCharacters(characterIds,location.residents.size)
                    }
                    else{
                        binding.characterRv.adapter = null
                    }
                }

        })
        binding.locationRv.adapter = adapterLocation
        viewModel.getFirstLocation()
//        if (firstLocation == null) {
//            firstLocation = it.results?.get(0)?.residents
//            val ids = viewModel.selectIds(firstLocation)
//            if (ids != null) {
//                viewModel.getMultipleCharacters(ids,firstLocation!!.size)
//            }
//        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listLocation.collect { pagingData ->
                adapterLocation.submitData(pagingData)
            }
        }
    }
}