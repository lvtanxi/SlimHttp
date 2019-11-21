package com.slim.http.sample.repository

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Api {

    @GET("join")
    fun join(): Deferred<Any>

    @GET("notjoin")
    fun notJoin(): Deferred<Any>
}