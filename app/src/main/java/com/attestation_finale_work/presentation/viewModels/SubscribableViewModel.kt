package com.attestation_finale_work.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attestation_finale_work.R
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.utils.DisplayInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class SubscribableViewModel(
    private val mainRepository: MainRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    
    private val _subscribeChannel = Channel<ApiResult<Int>>()
    val subscribeChannel = _subscribeChannel.receiveAsFlow()
    
    fun subscribeUnsubscribe(displayName: String, isUserSubscriber: Boolean, position: Int) {
        viewModelScope.launch (ioDispatcher) {
            _subscribeChannel.send(ApiResult.Loading(position))
            kotlin.runCatching {
                if (isUserSubscriber) {
                    mainRepository.subscribeUnsubscribe("unsub", displayName)
                } else {
                    mainRepository.subscribeUnsubscribe("sub", displayName)
                }
            }.fold(
                onSuccess = { _subscribeChannel.send(ApiResult.Success(position)) },
                onFailure = {
                    _subscribeChannel.send(
                        ApiResult.Error(DisplayInfo.ResourceString(R.string.something_went_wrong))
                    )
                }
            )
        }
    }
}