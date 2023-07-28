package com.attestation_finale_work.presentation.screens.favorite

import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.attestation_finale_work.R
import com.attestation_finale_work.presentation.screens.discrete.DiscretePostsFragment
import com.attestation_finale_work.presentation.viewModels.favourite.FavoritePostsViewModel

class FavoritePostsFragment : DiscretePostsFragment() {
    
    override val viewModel: FavoritePostsViewModel by viewModels()
    override val apiQuery = ""
    
    override fun setUpPageAdapter() {
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        divider.setDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.item_divider)!!)
        binding.recyclerView.apply {
            adapter = postsPageAdapter
            addItemDecoration(divider)
        }
    }
    
    override fun observeSubredditUpdates(currentSubredditId: String) = Unit
    
    override fun observeSubscribeResult() = Unit
}