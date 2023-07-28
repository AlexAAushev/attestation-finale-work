package com.attestation_finale_work.presentation.viewModels.subreddits

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.domain.SubredditThing
import com.attestation_finale_work.presentation.viewModels.SubscribableViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class AbstractSubredditsViewModel(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SubscribableViewModel(mainRepository, ioDispatcher) {
    
    abstract fun getPagedSubreddits(query: String): Flow<PagingData<SubredditThing>>
    
    fun saveCurrentSubreddit(subreddit: SubredditThing) {
        viewModelScope.launch(ioDispatcher) {
            mainRepository.saveCurrentSubreddit(subreddit)
        }
    }
}