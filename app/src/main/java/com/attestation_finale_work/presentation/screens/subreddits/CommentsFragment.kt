package com.attestation_finale_work.presentation.screens.subreddits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentCommentsBinding
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.utils.DisplayInfo
import com.attestation_finale_work.presentation.adapters.CommentsAdapter
import com.attestation_finale_work.presentation.adapters.MarginItemDecoration
import com.attestation_finale_work.presentation.screens.discrete.DiscreteCommonFragment
import com.attestation_finale_work.presentation.viewModels.subreddits.CommentsViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class CommentsFragment : DiscreteCommonFragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommentsViewModel by viewModels()
    private val commentsAdapter = CommentsAdapter(
        onAuthorClick = { onAuthorClick(it) },
        onVoteClick = { dir, id, position -> viewModel.handleVote(dir, id, position) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("postId").also { postId ->
            postId?.let { viewModel.getComments(it) }
        }
        observeComments()
        setUpPageAdapter()
        observeVoteResult()
    }

    private fun setUpPageAdapter() {
        binding.recyclerView.apply {
            adapter = commentsAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.recyclerview_dimen
                    )
                )
            )
        }
    }

    private fun observeComments() {
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
                            commentsAdapter.setData(it.data!!)
                        }
                    }
                }
            }
        }
    }

    private fun onAuthorClick(userName: String) {
        findNavController().navigate(
            R.id.from_comments_to_user_info,
            Bundle().also {
                it.putString("userName", userName)
            }
        )
    }

    private fun observeVoteResult() {
        lifecycleScope.launchWhenStarted {
            viewModel.voteChannel.collect {
                if (it is ApiResult.Error) {
                    showToast(
                        (it.errorMessage
                            ?: DisplayInfo.ResourceString(R.string.something_went_wrong)).asString(
                            requireContext()
                        )
                    )
                }
                if (it is ApiResult.Success) {
                    showToast("Successfully")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}