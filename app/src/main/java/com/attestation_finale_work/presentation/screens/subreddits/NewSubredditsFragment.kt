package com.attestation_finale_work.presentation.screens.subreddits

import androidx.fragment.app.viewModels
import com.attestation_finale_work.presentation.screens.discrete.DiscreteSubredditsFragment
import com.attestation_finale_work.presentation.viewModels.subreddits.NewSubredditsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewSubredditsFragment : DiscreteSubredditsFragment() {
    
    override val request = "new"
    
    override val viewModel: NewSubredditsViewModel by viewModels()
}