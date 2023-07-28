package com.attestation_finale_work.presentation.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentOnboardingBinding
import com.attestation_finale_work.presentation.PageTransformer
import com.attestation_finale_work.presentation.screens.discrete.DiscreteCommonFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : DiscreteCommonFragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onBoardingAdapter = OnBoardingViewPagerAdapter { onSkipClick() }

        binding.viewPager.adapter = onBoardingAdapter
        binding.viewPager.setPageTransformer(PageTransformer())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.skipButton.setOnClickListener {
            onSkipClick()
        }
    }

    private fun onSkipClick() = findNavController().navigate(R.id.from_onboarding_to_auth)
}