package com.attestation_finale_work.presentation.screens.subreddits

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.attestation_finale_work.presentation.screens.discrete.DiscreteSubredditsFragment
import com.attestation_finale_work.presentation.viewModels.subreddits.SearchSubredditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSubredditFragment : DiscreteSubredditsFragment() {
    
    override var request = ""
    
    override val viewModel: SearchSubredditViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        request = arguments?.getString("searchQuery") ?: ""
        super.onViewCreated(view, savedInstanceState)
    }
}