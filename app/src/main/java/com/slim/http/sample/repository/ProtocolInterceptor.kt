package com.http.corx.repository

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException

/**
 * Date: 2017-06-21
 * Time: 15:30
 * Description:
 */
class ProtocolInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        chain ?: throw RuntimeException("this Interceptor.Chain is empty")
        val response = chain.proceed(chain.request())
        val medizType = MediaType.parse("application/json; chartset='utf-8'")
        //判断请求是否成功了，然后再根据业务处理
        if (response.isSuccessful) {
            val data = parseDataFromBody(response.body()?.string())
            return response.newBuilder().body(ResponseBody.create(medizType, data)).build()

        }
        throw  IOException("网络请求异常,请稍后重试!")
    }

    private fun parseDataFromBody(body: String?): String {
        try {
            val json = JSONObject(body)
            val code = json.optInt("code", 0)
            if (code == 200)
                return json.optString("data")
            throw IOException(json.optString("msg"))
        } catch (e: Exception) {
            throw  IOException("数据解析错误,请稍后重试!")
        }
    }

}