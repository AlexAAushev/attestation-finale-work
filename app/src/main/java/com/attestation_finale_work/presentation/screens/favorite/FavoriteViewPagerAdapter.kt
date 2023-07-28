package com.attestation_finale_work.presentation.screens.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoriteViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FavoriteSubredditsFragment()
            1 -> FavoritePostsFragment()
            else -> throw java.lang.IllegalArgumentException("Invalid position $position")
        }
    }
}