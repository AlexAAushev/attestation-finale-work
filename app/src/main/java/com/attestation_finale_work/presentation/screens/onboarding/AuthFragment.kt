package com.attestation_finale_work.presentation.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentAuthBinding
import com.attestation_finale_work.oauth_data.ApiResult.*
import com.attestation_finale_work.presentation.screens.discrete.DiscreteCommonFragment
import com.attestation_finale_work.presentation.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : DiscreteCommonFragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentData = it.data ?: return@registerForActivityResult
            viewModel.handleAuthResponseIntent(intentData)
        }

    private fun openAuthPage() = viewModel.prepareAuthPageIntent { authLauncher.launch(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.observe {
                if (it is Success) {
                    findNavController().navigate(R.id.from_auth_to_main)
                }
                if (it is Error) {
                    Toast.makeText(
                        requireContext(),
                        it.exception.asString(requireContext()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.authButton.setOnClickListener {
            openAuthPage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}