package com.attestation_finale_work.presentation.screens.discrete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentPostsBinding
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.utils.DisplayInfo
import com.attestation_finale_work.presentation.adapters.PostsPageAdapter
import com.attestation_finale_work.presentation.viewModels.PostsViesModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
abstract class DiscretePostsFragment : DiscreteCommonFragment() {

    protected abstract val viewModel: PostsViesModel
    private var _binding: FragmentPostsBinding? = null
    protected val binding get() = _binding!!
    abstract val apiQuery: String
    protected val postsPageAdapter = PostsPageAdapter(
        onPostClick = { onPostClick(it) },
        onVoteButtonsClick = { dir, postId, position -> onVoteClick(dir, postId, position) },
        onAuthorClick = { onAuthorClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSubredditUpdates(apiQuery)
        observePagerFlow(apiQuery)
        setUpPageAdapter()
        setupLoadStates()
        observeSubscribeResult()
        observeVoteResult()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setUpPageAdapter()

    private fun setupLoadStates() {
        binding.swipeRefresh.setOnRefreshListener {
            postsPageAdapter.refresh()
        }
        postsPageAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
            listOf(it.append, it.prepend, it.refresh).forEach { loadState ->
                if (loadState is LoadState.Error) {
                    showToast(
                        loadState.error.localizedMessage
                            ?: DisplayInfo.ResourceString(R.string.loading_error)
                                .asString(requireContext())
                    )
                }
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observePagerFlow(query: String) {
        viewModel.getPagedPosts(query).onEach {
            postsPageAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    protected abstract fun observeSubredditUpdates(currentSubredditId: String)

    protected abstract fun observeSubscribeResult()

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

    private fun onPostClick(postId: String) {
        findNavController().navigate(
            R.id.from_posts_to_comments,
            Bundle().also { it.putString("postId", postId) }
        )
    }

    private fun onAuthorClick(userName: String) {
        findNavController().navigate(
            R.id.from_posts_to_user_info,
            Bundle().also {
                it.putString("userName", userName)
            }
        )
    }

    open fun onVoteClick(dir: Int, postId: String, position: Int) {
        viewModel.handleVote(dir, postId, position)
    }
}