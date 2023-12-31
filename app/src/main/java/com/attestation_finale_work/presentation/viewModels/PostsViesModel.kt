package com.attestation_finale_work.presentation.viewModels

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.attestation_finale_work.R
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.SubredditThing
import com.attestation_finale_work.utils.DisplayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PostsViesModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SubscribableViewModel(mainRepository, ioDispatcher) {
    
    private val _voteChannel = Channel<ApiResult<Int>>()
    val voteChannel = _voteChannel.receiveAsFlow()
    
    fun handleVote(dir: Int, postId: String, position: Int) {
        viewModelScope.launch(ioDispatcher) {
            _voteChannel.send(ApiResult.Loading(position))
            kotlin.runCatching {
                mainRepository.vote(dir, postId)
            }.fold(
                onSuccess = { _voteChannel.send(ApiResult.Success(position)) },
                onFailure = {
                    _voteChannel.send(
                        ApiResult.Error(DisplayInfo.ResourceString(R.string.something_went_wrong))
                    )
                }
            )
        }
    }
    
    open fun getPagedPosts(subredditName: String) =
        mainRepository.getPagedPostsFlow(subredditName).cachedIn(viewModelScope)
    
    fun getCurrentSubreddit(subredditName: String): Flow<SubredditThing> {
        return mainRepository.getCurrentSubreddit(subredditName)
    }
}