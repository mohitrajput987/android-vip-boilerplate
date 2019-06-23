package com.otb.vipboilerplate.utils

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request

/**
 * Created by Mohit Rajput on 6/5/19.
 */
object NetworkUtils {
    /**
     * Checks whether internet is connected in device or not
     *
     * @param context
     * @return true if connected, false otherwise
     */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
        return false
    }

    fun prepareInterceptorRequest(chain: Interceptor.Chain): Request {
        val builder = chain.request().newBuilder()
        return builder.build()
    }
}
