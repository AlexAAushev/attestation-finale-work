package com.attestation_finale_work.presentation.viewModels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.DiscreteReddit
import com.attestation_finale_work.utils.ConnectionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val communication: ConnectionInfo.Base<ApiResult<DiscreteReddit>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    
    fun getUserInfo(userName: String) {
        viewModelScope.launch(ioDispatcher) {
            communication.map(ApiResult.Loading())
            val response = mainRepository.getUserInfo(userName)
            communication.map(response)
        }
    }
    
    suspend fun observe(collector: FlowCollector<ApiResult<DiscreteReddit>?>) {
        communication.observe(collector)
    }
}