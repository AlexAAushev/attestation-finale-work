package com.attestation_finale_work.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface DisplayInfo {
    
    fun asString(context: Context): String

    class DynamicString @Inject constructor(private val value: String): DisplayInfo {
        override fun asString(context: Context): String {
            return value
        }
    }
    
    class ResourceString(
        @StringRes private val resId: Int,
        private vararg val args: Any
    ) : DisplayInfo {
        override fun asString(context: Context): String {
            return context.getString(resId, *args)
        }
    }
}
