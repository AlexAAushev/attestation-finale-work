package com.attestation_finale_work.data

import com.attestation_finale_work.R
import com.attestation_finale_work.data.db.entity.CommonResponse
import com.attestation_finale_work.data.db.entity.EntityData
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.DiscreteReddit
import com.attestation_finale_work.utils.DisplayInfo
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

interface SafeRemoteRepo {

    suspend fun <Data> safeApiCall(apiToBeCalled: suspend () -> Response<Data>): ApiResult<Data>

    suspend fun wrapListResponse(apiToBeCalled: suspend () -> Response<CommonResponse>): ApiResult<List<DiscreteReddit>>

    suspend fun wrapResponse(apiToBeCalled: suspend () -> Response<EntityData>): ApiResult<DiscreteReddit>

    abstract class BaseRemoteRepo : SafeRemoteRepo {

        override suspend fun <Data> safeApiCall(apiToBeCalled: suspend () -> Response<Data>): ApiResult<Data> {
            return try {
                val response = apiToBeCalled()

                if (response.isSuccessful) {
                    ApiResult.Success(_data = response.body())
                } else {
                    ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.check_connection))
                }
            } catch (e: HttpException) {
                ApiResult.Error(
                    exception = if (e.message != null) DisplayInfo.DynamicString(e.message())
                    else DisplayInfo.ResourceString(R.string.something_went_wrong)
                )
            } catch (e: IOException) {
                ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.check_connection))
            } catch (e: Exception) {
                ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.something_went_wrong))
            }
        }

        override suspend fun wrapListResponse(apiToBeCalled: suspend () -> Response<CommonResponse>): ApiResult<List<DiscreteReddit>> {
            return try {

                val response = apiToBeCalled()
                if (response.isSuccessful) {
                    ApiResult.Success(_data = response.body()?.commonData?.data?.map { it.data.map() })
                } else {

                    ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.check_connection))
                }
            } catch (e: HttpException) {
                ApiResult.Error(
                    exception = if (e.message != null) DisplayInfo.DynamicString(e.message())
                    else DisplayInfo.ResourceString(R.string.something_went_wrong)
                )
            } catch (e: IOException) {
                ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.check_connection))
            } catch (e: Exception) {
                ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.something_went_wrong))
            }
        }

        override suspend fun wrapResponse(apiToBeCalled: suspend () -> Response<EntityData>): ApiResult<DiscreteReddit> {
            return try {

                val response = apiToBeCalled()
                if (response.isSuccessful) {
                    ApiResult.Success(_data = response.body()?.data?.map())
                } else {

                    ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.check_connection))
                }
            } catch (e: HttpException) {
                ApiResult.Error(
                    exception = if (e.message != null) DisplayInfo.DynamicString(e.message())
                    else DisplayInfo.ResourceString(R.string.something_went_wrong)
                )
            } catch (e: IOException) {
                ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.check_connection))
            } catch (e: Exception) {
                ApiResult.Error(exception = DisplayInfo.ResourceString(R.string.something_went_wrong))
            }
        }
    }
}

