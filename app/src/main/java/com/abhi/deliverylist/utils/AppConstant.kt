package com.abhi.deliverylist.utils

import androidx.annotation.IntDef
import com.abhi.deliverylist.BuildConfig
import com.abhi.deliverylist.utils.BottomDialogType.Companion.ALL_DATA_FETCHED
import com.abhi.deliverylist.utils.BottomDialogType.Companion.ERROR_IN_FETCHING

import okhttp3.logging.HttpLoggingInterceptor


val sHTTP_LOG_LEVEL = if (BuildConfig.DEBUG)
    HttpLoggingInterceptor.Level.BODY
else
    HttpLoggingInterceptor.Level.NONE

@IntDef(
    ALL_DATA_FETCHED,
    ERROR_IN_FETCHING
)

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class BottomDialogType {
    companion object {
        const val ALL_DATA_FETCHED = 1
        const val ERROR_IN_FETCHING = 2
    }
}

