package com.attestation_finale_work.data

import androidx.paging.*
import com.attestation_finale_work.data.db.dao.SavedSubredditsDao
import com.attestation_finale_work.data.db.dto.PostDto
import com.attestation_finale_work.data.db.dto.SubredditDto
import com.attestation_finale_work.data.db.dto.UserDto
import com.attestation_finale_work.data.db.entity.CommonResponse
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.*
import com.attestation_finale_work.networking.RedditApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dataStoreManager: DataStoreManager.Base,
    private val redditApi: RedditApi,
    private val cachedSubredditsDao: SavedSubredditsDao
) : SafeRemoteRepo.BaseRemoteRepo() {

    fun getPagedSubredditsFlow(query: String): Flow<PagingData<SubredditThing>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = {
            GenericPagingSource { afterKey ->
                redditApi.loadSubreddits(query, afterKey)
            }
        }
    ).flow.transform { pagingData -> emit(pagingData.map { (it.data as SubredditDto).map() }) }

    fun getPagedPostsFlow(subredditName: String): Flow<PagingData<PostThing>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = {
            GenericPagingSource { afterKey ->
                redditApi.loadSubredditPosts(subredditName, afterKey)
            }
        }
    ).flow.transform { pagingData ->
        emit(pagingData.map { (it.data as PostDto).map() })
    }.transform { pagingData ->
        emit(pagingData.filter { it is ImagePostEntity || it is TextPostEntity })
    }

    fun getPagedFavoriteSubreddits(): Flow<PagingData<SubredditThing>> = Pager(
        config = PagingConfig(25),
        pagingSourceFactory = {
            GenericPagingSource { afterKey ->
                redditApi.loadFavoriteSubreddits(afterKey)
            }
        }
    ).flow.transform { pagingData -> emit(pagingData.map { (it.data as SubredditDto).map() }) }

    fun getPagedSearchedSubredditsFlow(query: String): Flow<PagingData<SubredditThing>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = {
            GenericPagingSource { afterKey ->
                redditApi.searchSubreddits(query, afterKey)
            }
        }
    ).flow.transform { pagingData -> emit(pagingData.map { (it.data as SubredditDto).map() }) }

    fun getPagedFavoritePosts(): Flow<PagingData<PostThing>> = Pager(
        config = PagingConfig(25),
        pagingSourceFactory = {
            GenericPagingSource { afterKey ->
                redditApi.loadFavoritePosts(getUserName(), afterKey)
            }
        }
    ).flow.transform { pagingData -> emit(pagingData.map { (it.data as PostDto).map() }) }

    suspend fun getComments(postId: String): Response<List<CommonResponse>> {
        return redditApi.getComments(postId)
    }

    suspend fun saveCurrentSubreddit(subreddit: SubredditThing) =
        cachedSubredditsDao.saveSubreddit(subreddit)

    fun getCurrentSubreddit(subredditName: String): Flow<SubredditThing> {
        return cachedSubredditsDao.getSubreddit(subredditName)
    }

    suspend fun saveAccessToken(token: String) {
        dataStoreManager.saveToken(token)
    }

    suspend fun checkAccessToken(): Boolean {
        return dataStoreManager.checkToken()
    }

    suspend fun vote(direction: Int, postId: String): ApiResult<Unit> {
        return safeApiCall { redditApi.vote(direction, postId) }
    }

    suspend fun subscribeUnsubscribe(action: String, displayName: String): ApiResult<Unit> {
        return safeApiCall { redditApi.subscribeUnsubscribe(action, displayName) }
    }

    suspend fun getMyProfile(): ApiResult<UserDto> {
        return safeApiCall { redditApi.getMyProfile() }
    }

    suspend fun getUserInfo(userName: String): ApiResult<DiscreteReddit> {
        return wrapResponse { redditApi.getUserInfo(userName) }
    }

    suspend fun getUserName(): String {
        return redditApi.getMyProfile().body()?.name ?: ""
    }

    suspend fun logOut() {
        cachedSubredditsDao.clearAll()
        dataStoreManager.clearDataStore()
    }
}
