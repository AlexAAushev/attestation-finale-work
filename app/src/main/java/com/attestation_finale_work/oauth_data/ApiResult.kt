package com.attestation_finale_work.oauth_data

import com.attestation_finale_work.utils.DisplayInfo

sealed class ApiResult<out T>(
    val data: T?,
    val errorMessage: DisplayInfo?
) {

    class Success<out T>(_data: T?) : ApiResult<T>(
        data = _data,
        errorMessage = null
    )

    class Error<out T>(
        val exception: DisplayInfo
    ) : ApiResult<T>(
        data = null,
        errorMessage = exception
    )

    class Loading<out T>(_data: T? = null) : ApiResult<T>(
        data = _data,
        errorMessage = null
    )
}