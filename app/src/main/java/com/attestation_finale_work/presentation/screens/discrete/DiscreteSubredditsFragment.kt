package com.attestation_finale_work.presentation.screens.discrete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.FragmentSubredditsGenericBinding
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.SubredditThing
import com.attestation_finale_work.utils.DisplayInfo
import com.attestation_finale_work.presentation.adapters.MarginItemDecoration
import com.attestation_finale_work.presentation.adapters.PageLoadStateAdapter
import com.attestation_finale_work.presentation.adapters.SubredditsPageAdapter
import com.attestation_finale_work.presentation.viewModels.subreddits.AbstractSubredditsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class DiscreteSubredditsFragment : DiscreteCommonFragment() {
    
    protected abstract val request: String
    protected abstract val viewModel: AbstractSubredditsViewModel
    private var _binding: FragmentSubredditsGenericBinding? = null
    private val binding get() = _binding!!
    private val subredditsPageAdapter = SubredditsPageAdapter(
        onItemClick = { onSubredditClick(it) }
    ) { name, isSubscribed, position ->
        viewModel.subscribeUnsubscribe(
            name,
            isSubscribed,
            position
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubredditsGenericBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setUpPageAdapter()
        setupLoadStates()
        observePagerFlow()
        observeSubscribeResult()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    
    private fun setUpPageAdapter() {
        binding.recyclerView.apply {
            adapter = subredditsPageAdapter.withLoadStateFooter(PageLoadStateAdapter())
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.recyclerview_dimen
                    )
                )
            )
        }
    }
    
    private fun setupLoadStates() {
        binding.swipeRefresh.setOnRefreshListener {
            subredditsPageAdapter.refresh()
        }
        subredditsPageAdapter.loadStateFlow.onEach {
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
            if (it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && subredditsPageAdapter.itemCount < 1) {
                binding.notFoundItem.root.visibility = View.VISIBLE
                binding.notFoundItem.backButton.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    
    private fun observePagerFlow() {
        viewModel.getPagedSubreddits(request).onEach {
            subredditsPageAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    
    private fun observeSubscribeResult() {
        lifecycleScope.launchWhenStarted {
            viewModel.subscribeChannel.collect { result ->
                if (result is ApiResult.Error) {
                    showToast(
                        DisplayInfo.ResourceString(R.string.something_went_wrong)
                            .asString(requireContext())
                    )
                } else {
                    subredditsPageAdapter.updateElement(result)
                }
            }
        }
    }

    private fun onSubredditClick(subreddit: SubredditThing) {
        viewModel.saveCurrentSubreddit(subreddit)
        findNavController().navigate(
            R.id.from_subreddits_to_posts,
            Bundle().also { it.putString("subredditName", subreddit.displayName) }
        )
    }
}