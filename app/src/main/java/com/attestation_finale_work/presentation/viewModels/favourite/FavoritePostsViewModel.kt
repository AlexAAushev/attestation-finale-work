package com.attestation_finale_work.presentation.viewModels.favourite

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.domain.PostThing
import com.attestation_finale_work.presentation.viewModels.PostsViesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritePostsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PostsViesModel(mainRepository, ioDispatcher) {
    
    override fun getPagedPosts(subredditName: String): Flow<PagingData<PostThing>> {
        return mainRepository.getPagedFavoritePosts().cachedIn(viewModelScope)
    }
}