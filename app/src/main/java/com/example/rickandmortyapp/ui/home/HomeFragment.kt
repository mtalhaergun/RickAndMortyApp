package com.example.rickandmortyapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateFinished() {

    }

    override fun observeEvents() {

    }



}