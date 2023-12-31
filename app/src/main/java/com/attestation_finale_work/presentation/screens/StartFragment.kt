package com.attestation_finale_work.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.attestation_finale_work.R
import com.attestation_finale_work.presentation.screens.discrete.DiscreteCommonFragment
import com.attestation_finale_work.presentation.viewModels.StartViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : DiscreteCommonFragment() {
    
    private val viewModel: StartViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.checkAccessToken()
        
        lifecycleScope.launchWhenStarted {
            viewModel.observe {
                if (it == true) findNavController().navigate(R.id.from_start_to_main)
                if (it == false) findNavController().navigate(R.id.from_start_to_onboarding)
            }
        }
    }
    
}
