package com.attestation_finale_work.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.attestation_finale_work.data.db.entity.CommonResponse
import com.attestation_finale_work.data.db.entity.EntityData
import retrofit2.Response
import javax.inject.Inject

class GenericPagingSource @Inject constructor(
    private val apiRequest: suspend (String) -> Response<CommonResponse>
) : PagingSource<String, EntityData>() {

    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<String, EntityData>): String = ""

    override suspend fun load(params: LoadParams<String>): LoadResult<String, EntityData> {

        val after = params.key ?: ""
        return kotlin.runCatching {
            apiRequest(after)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.body()?.commonData?.data
                        ?: emptyList(),
                    prevKey = null,
                    nextKey = it.body()?.commonData?.after
                )
            },
            onFailure = { throw it }
        )
    }
}