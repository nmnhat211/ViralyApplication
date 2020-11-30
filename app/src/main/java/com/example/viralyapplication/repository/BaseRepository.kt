package com.example.viralyapplication.repository

import android.app.Application
import com.example.viralyapplication.utils.NetworkUtils
import retrofit2.Response

open class BaseRepository(application: Application) {
    private val STATUS_OK = "OK"
    private val STATUS_SUCCESS = "SUCCESS"
    protected val mApplication: Application = application

    suspend fun <T> apicall() {
        if (!NetworkUtils.isNetworkConnected(mApplication)) {
            return
        }
    }
}