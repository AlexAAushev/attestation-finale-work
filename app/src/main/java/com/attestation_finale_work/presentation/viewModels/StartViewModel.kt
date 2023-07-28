package com.attestation_finale_work.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.utils.ConnectionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val communication: ConnectionInfo.Base<Boolean>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    
    fun checkAccessToken() {
        viewModelScope.launch(ioDispatcher) {
            val result = mainRepository.checkAccessToken()
            communication.map(result)
        }
    }
    
    suspend fun observe(collector: FlowCollector<Boolean?>) {
        communication.observe(collector)
    }
}