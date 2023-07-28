package com.attestation_finale_work.presentation.viewModels.subreddits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attestation_finale_work.R
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.CommentThing
import com.attestation_finale_work.utils.ConnectionInfo
import com.attestation_finale_work.utils.DisplayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val communication: ConnectionInfo.Base<ApiResult<List<CommentThing>>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _voteChannel = Channel<ApiResult<Int>>()
    val voteChannel = _voteChannel.receiveAsFlow()

    fun getComments(postId: String) {
        viewModelScope.launch(ioDispatcher) {
            communication.map(ApiResult.Loading())
            val response = mainRepository.getComments(postId)
            val comments = response.body()?.last()
            comments?.commonData?.data?.map { it.data.map() }?.filterIsInstance<CommentThing>()
                .also { communication.map(ApiResult.Success(it)) }
        }
    }

    suspend fun observe(collector: FlowCollector<ApiResult<List<CommentThing>>?>) {
        communication.observe(collector)
    }

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
}