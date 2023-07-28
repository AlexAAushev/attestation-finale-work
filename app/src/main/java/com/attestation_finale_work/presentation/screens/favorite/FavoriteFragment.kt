package com.attestation_finale_work.presentation.screens.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentFavoriteBinding
import com.attestation_finale_work.presentation.screens.discrete.DiscreteCommonFragment
import com.google.android.material.tabs.TabLayoutMediator

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : DiscreteCommonFragment() {
    
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        val pageAdapter = FavoriteViewPagerAdapter(this)
    
        val viewPager = binding.favouritePager
        viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.subreddits)
                1 -> tab.text = getString(R.string.posts)
            }
        }.attach()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}