package com.otb.vipboilerplate.network

import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by Mohit Rajput on 6/5/19.
 * Interceptor to add headers azure function calls
 */

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", "07d4af731f270289edb42caa8b71f4f3")
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
