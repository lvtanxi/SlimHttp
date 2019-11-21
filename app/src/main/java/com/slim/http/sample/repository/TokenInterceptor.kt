package com.http.corx.repository

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Date: 2017-06-21
 * Time: 18:09
 * Description:
 */
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        chain ?: throw RuntimeException("this Interceptor.Chain is empty")
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()
                .addHeader("authInfo", "sadfasdfasdfasd")
                .method(originalRequest.method(), originalRequest.body())
        return chain.proceed(newBuilder.build())
    }
}