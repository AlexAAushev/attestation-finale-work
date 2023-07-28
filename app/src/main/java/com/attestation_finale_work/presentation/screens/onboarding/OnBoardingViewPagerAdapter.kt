package com.attestation_finale_work.presentation.screens.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.OnboardingPageBinding

class OnBoardingViewPagerAdapter(
    private val onSkipClick: () -> Unit
) : RecyclerView.Adapter<OnBoardingPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingPagerViewHolder {
        return OnBoardingPagerViewHolder(
            OnboardingPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = MAX_PAGE

    override fun onBindViewHolder(holder: OnBoardingPagerViewHolder, position: Int) =
        holder.itemView.run {
            with(holder.binding) {
                when (position) {
                    0 -> {
                        onboardingImage.setImageResource(R.drawable.onboarding_1)
                        onboardingTitle.text = context.getString(R.string.onboarding_title_1)
                        onboardingText.text = context.getString(R.string.onboarding_text_1)
                        bottomSkipButton.visibility = View.GONE
                    }
                    1 -> {
                        onboardingImage.setImageResource(R.drawable.onboarding_2)
                        onboardingTitle.text = context.getString(R.string.onboarding_title_2)
                        onboardingText.text = context.getString(R.string.onboarding_text_2)
                        bottomSkipButton.visibility = View.GONE
                    }
                    2 -> {
                        onboardingImage.setImageResource(R.drawable.onboarding_3)
                        onboardingTitle.text = context.getString(R.string.onboarding_title_3)
                        onboardingText.text = context.getString(R.string.onboarding_text_3)
                        bottomSkipButton.visibility = View.VISIBLE
                    }
                }
                bottomSkipButton.setOnClickListener {
                    onSkipClick.invoke()
                }
            }
        }

    companion object {
        const val MAX_PAGE = 3
    }
}

class OnBoardingPagerViewHolder(val binding: OnboardingPageBinding) :
    RecyclerView.ViewHolder(binding.root)