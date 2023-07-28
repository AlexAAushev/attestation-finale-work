package com.attestation_finale_work.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentUserInfoBinding
import com.bumptech.glide.Glide
import com.attestation_finale_work.domain.UserEntity
import com.attestation_finale_work.utils.DisplayInfo
import com.attestation_finale_work.presentation.screens.discrete.DiscreteCommonFragment

abstract class AbstractUserFragment : DiscreteCommonFragment() {

    abstract val viewModel: ViewModel
    protected var _binding: FragmentUserInfoBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfileData()
    }

    abstract fun observeProfileData()

    fun setDataToView(data: UserEntity) {
        Glide.with(this)
            .load(data.iconImg)
            .into(binding.avatar)
        binding.userName.text = data.name
        binding.created.text =
            DisplayInfo.ResourceString(R.string.created, data.created).asString(requireContext())
        binding.karma.text =
            DisplayInfo.ResourceString(R.string.karma, data.karma.toString())
                .asString(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}