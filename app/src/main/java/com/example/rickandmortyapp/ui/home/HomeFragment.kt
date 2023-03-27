package com.example.rickandmortyapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.FragmentHomeBinding
import com.example.rickandmortyapp.model.character.CharacterResponseItem
import com.example.rickandmortyapp.model.location.Result
import com.example.rickandmortyapp.ui.home.adapters.CharacterRecyclerAdapter
import com.example.rickandmortyapp.ui.home.adapters.LoadAdapter
import com.example.rickandmortyapp.ui.home.adapters.LocationRecyclerAdapter
import com.example.rickandmortyapp.ui.home.listeners.CharacterClickListener
import com.example.rickandmortyapp.ui.home.listeners.LocationClickListener
import com.example.rickandmortyapp.utils.Constants.CHARACTERLIMIT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var adapterLocation: LocationRecyclerAdapter
    private lateinit var adapterCharacter : CharacterRecyclerAdapter
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var firstOpen : Boolean = true
    private var sameLocationClick : Result? = null
    private var characterPagingList = arrayListOf<CharacterResponseItem>()
    private var tempList = arrayListOf<CharacterResponseItem>()
    var page = 1
    var tempPage = 0
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(firstOpen) createRv()
        loadLocations()
        observeEvents()
    }

    fun observeEvents() {

        viewModel.multipleCharacterResponse.observe(viewLifecycleOwner, Observer {
            isLoading = true

            if(binding.characterRv.adapter == null){
                binding.characterRv.adapter = adapterCharacter
            }

            it?.let {
                var start = (page-1)*CHARACTERLIMIT
                var end = (page*(CHARACTERLIMIT))-1

                if(characterPagingList.size != it.lastIndex+1 && tempPage != page){
                    for(i in start..end){
                        if(i < it.lastIndex+1){
                            characterPagingList.add(it[i])
                        }
                    }
                    tempPage = page
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    adapterCharacter.setCharacters(characterPagingList)
                }, 500)
                tempList = it as ArrayList<CharacterResponseItem>
            }
            isLoading = false

            binding.characterRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val visibleItemCount = (binding.characterRv.layoutManager as? LinearLayoutManager)?.childCount!!
                    val pastVisibleItem = (binding.characterRv.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()!!
                    val total = adapterCharacter.itemCount

                    if(!isLoading){
                        if((visibleItemCount + pastVisibleItem) > total && (total%CHARACTERLIMIT) == 0){
                            page++
                            viewModel.multipleCharacterResponse.value = tempList
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
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
                if(sameLocationClick != location){
                    sameLocationClick = location
                    binding.gifNoLocation.visibility = View.GONE
                    val characterIds = viewModel.selectIds(location.residents)
                    if (characterIds != null) {
                        binding.characterRv.adapter = null
                        characterPagingList.clear()
                        page = 1
                        tempPage = 0
                        viewModel.getMultipleCharacters(characterIds,location.residents.size)
                    }
                    else{
                        binding.characterRv.adapter = null
                        Toast.makeText(requireContext(),"Empty Location",Toast.LENGTH_SHORT).show()
                        binding.gifNoLocation.visibility = View.VISIBLE
                    }
                }
            }

        })
        binding.locationRv.adapter = adapterLocation

        adapterCharacter = CharacterRecyclerAdapter(object : CharacterClickListener{
            override fun onCharacterClick(character: CharacterResponseItem) {
                val navigation = HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
                Navigation.findNavController(requireView()).navigate(navigation)
            }
        })
        binding.characterRv.adapter = adapterCharacter
        viewModel.getFirstLocation()
    }

    private fun loadLocations() {
        lifecycleScope.launch {
            binding.locationRv.adapter = adapterLocation.withLoadStateFooter(LoadAdapter())
            viewModel.listLocation.collect { pagingData ->
                adapterLocation.submitData(pagingData)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}