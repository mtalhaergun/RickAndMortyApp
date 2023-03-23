package com.example.rickandmortyapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.FragmentDetailBinding
import com.example.rickandmortyapp.ui.home.HomeFragmentDirections
import com.example.rickandmortyapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding?= null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        binding.character = args.chrctr
        binding.episodes.setText(splitEpisodes(args.chrctr.episode))
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }



    fun splitEpisodes(episodeList : List<String?>?) : String?{
        var epList: List<String>?
        var episodes : String? = null
        if (episodeList != null) {
            for(item in episodeList){
                if (item != null) {
                    epList = item.split(Constants.DELIMITEREPISODE)
                    if(episodes == null){
                        episodes = epList[1]
                    }else{
                        episodes = episodes + ", " + epList[1]
                    }
                }
            }
        }
        return episodes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}