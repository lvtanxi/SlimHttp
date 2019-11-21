package com.slim.http.sample.repository

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import com.slim.http.delegate.CoroutineAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object HttpRepository {
     fun getApiService(): Api {
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ProtocolInterceptor())
  /*      addHttps(clientBuilder)*/
        return Retrofit.Builder()
                .baseUrl("http://10.12.194.93:1111/student/order/")
                .client(clientBuilder.build())
                .addCallAdapterFactory(CoroutineAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(Api::class.java)
    }


    @SuppressLint("TrustAllX509TrustManager")
    private fun addHttps(builder: OkHttpClient.Builder) {
        try {
            val sc = SSLContext.getInstance("SSL")

            sc.init(null, arrayOf(object : X509TrustManager {


                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            }), SecureRandom())
            builder.sslSocketFactory(sc.socketFactory, object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

            })
            builder.hostnameVerifier { _, _ -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}