package com.attestation_finale_work.presentation.viewModels.subreddits

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class PopularSubredditsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AbstractSubredditsViewModel(mainRepository, ioDispatcher) {
    
    override fun getPagedSubreddits(query: String) =
        mainRepository.getPagedSubredditsFlow(query).cachedIn(viewModelScope)
}