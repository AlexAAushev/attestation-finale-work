package com.attestation_finale_work.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentUserInfoBinding
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.UserEntity
import com.attestation_finale_work.utils.DisplayInfo
import com.attestation_finale_work.presentation.viewModels.profile.UserViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : AbstractUserFragment() {
    
    override val viewModel: UserViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("userName")?.let { viewModel.getUserInfo(it) }
        
        binding.logoutButton.visibility = View.GONE
        
    }
    
    override fun observeProfileData() {
        lifecycleScope.launchWhenStarted {
            viewModel.observe {
                if (it != null) {
                    when (it) {
                        is ApiResult.Loading -> {
                            binding.progressLayout.visibility = View.VISIBLE
                        }
                        is ApiResult.Error -> {
                            binding.progressLayout.visibility = View.GONE
                            showToast(
                                (it.errorMessage
                                    ?: DisplayInfo.ResourceString(R.string.something_went_wrong)).asString(
                                    requireContext()
                                )
                            )
                        }
                        is ApiResult.Success -> {
                            binding.progressLayout.visibility = View.GONE
                            it.data?.let { setDataToView(it as UserEntity) }
                        }
                    }
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}