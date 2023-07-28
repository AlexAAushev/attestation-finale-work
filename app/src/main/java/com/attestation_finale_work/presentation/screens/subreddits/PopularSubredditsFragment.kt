package com.attestation_finale_work.presentation.screens.subreddits

import androidx.fragment.app.viewModels
import com.attestation_finale_work.presentation.screens.discrete.DiscreteSubredditsFragment
import com.attestation_finale_work.presentation.viewModels.subreddits.PopularSubredditsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularSubredditsFragment : DiscreteSubredditsFragment() {
    
    override val request = "popular"
    
    override val viewModel: PopularSubredditsViewModel by viewModels()
}