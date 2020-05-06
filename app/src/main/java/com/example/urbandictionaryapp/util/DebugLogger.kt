package com.example.urbandictionaryapp.util

import android.util.Log
import com.example.urbandictionaryapp.util.Constants.Companion.ERROR_PREFIX
import com.example.urbandictionaryapp.util.Constants.Companion.TAG

object DebugLogger {

    fun logError(throwable: Throwable) {
        Log.d(TAG, ERROR_PREFIX + throwable.localizedMessage)
    }

    fun logDebug(message: String) {
        Log.d(TAG, message)
    }


}