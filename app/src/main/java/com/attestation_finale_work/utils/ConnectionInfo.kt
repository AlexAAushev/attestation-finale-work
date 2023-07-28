package com.attestation_finale_work.utils

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface ConnectionInfo<T : Any> {

    fun map(data: T)

    suspend fun observe(collector: FlowCollector<T?>)

    class Base<T : Any> @Inject constructor() : ConnectionInfo<T> {

        private val mutableStateFlow = MutableStateFlow<T?>(null)

        override fun map(data: T) {
            mutableStateFlow.value = data
        }

        override suspend fun observe(collector: FlowCollector<T?>) {
            mutableStateFlow.collect(collector)
        }
    }
}