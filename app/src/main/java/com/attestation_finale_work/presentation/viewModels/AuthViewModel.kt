package com.attestation_finale_work.presentation.viewModels

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attestation_finale_work.R
import com.attestation_finale_work.data.MainRepository
import com.attestation_finale_work.di.IoDispatcher
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.oauth_data.AppAuth
import com.attestation_finale_work.utils.ConnectionInfo
import com.attestation_finale_work.utils.DisplayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthorizationService,
    private val mainRepository: MainRepository,
    private val communication: ConnectionInfo.Base<ApiResult<DisplayInfo>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun handleAuthResponseIntent(intent: Intent) {
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest = AppAuth.prepareTokenRequest(intent)

        viewModelScope.launch(ioDispatcher) {
            if (exception != null) {
            } else {
                onAuthCodeReceived(tokenExchangeRequest)
            }
        }
    }

    private suspend fun onAuthCodeReceived(tokenExchangeRequest: TokenRequest) {
        runCatching {
            communication.map(ApiResult.Loading())
            AppAuth.performTokenRequestSuspend(
                authService = authService,
                tokenRequest = tokenExchangeRequest
            )
        }.onSuccess {
            mainRepository.saveAccessToken(it)
            communication.map(ApiResult.Success(DisplayInfo.DynamicString(it)))
        }.onFailure {
            val errorMessage =
                if (it.message == null) DisplayInfo.ResourceString(R.string.authcode_receiving_error)
                else DisplayInfo.DynamicString(it.message!!)

            communication.map(ApiResult.Error(errorMessage))
        }
    }

    suspend fun observe(collector: FlowCollector<ApiResult<DisplayInfo>?>) {
        communication.observe(collector)
    }

    fun prepareAuthPageIntent(openAuthPage: (Intent) -> Unit) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = AppAuth.getAuthorizationRequest()

        val authPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
        openAuthPage(authPageIntent)
    }
}