package com.attestation_finale_work.presentation.screens.favorite

import androidx.fragment.app.viewModels
import com.attestation_finale_work.presentation.screens.discrete.DiscreteSubredditsFragment
import com.attestation_finale_work.presentation.viewModels.favourite.FavoriteSubredditsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteSubredditsFragment : DiscreteSubredditsFragment() {
    
    override val request = ""
    
    override val viewModel: FavoriteSubredditsViewModel by viewModels()
}