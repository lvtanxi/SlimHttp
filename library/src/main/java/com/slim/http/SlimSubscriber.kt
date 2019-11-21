package com.slim.http

import com.slim.http.intes.WidgetInterface
import java.lang.ref.WeakReference
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 *自定义的Subscriber
 *
 * @author melon
 * @version 1.0
 * @since JDK1.8
 */
open class SlimSubscriber<T>(
    widgetInterface: WidgetInterface,
    private val showLoading: Boolean = true,
    private val toast: Boolean = true
) {
    private val weakReference = WeakReference<WidgetInterface>(widgetInterface)
    private var data: T? = null


    /**
     * 不管错误或者成功都会回调
     */
    private var onFinish: Finish = null

    fun onFinish(action: Finish) = apply { onFinish = action }

    /**
     * 成功和实体不为空的时候回调
     */
    private var onSucess: Sucess<T> = null

    fun onSucess(action: Sucess<T>) = apply { onSucess = action }

    /**
     * 成功时候回调
     */
    private var onSuccessWithNull: SuccessWithNull<T> = null

    fun onSuccessWithNull(action: SuccessWithNull<T>) = apply { onSuccessWithNull = action }


    /**
     * 请求失败的时回调
     */
    private var onFailure: Failure = null

    fun onFailure(action: Failure) = apply { onFailure = action }

    fun onStart() {
        if (showLoading)
            weakReference.get()?.showLoadingView()
    }

    fun onComplete() {
        showMessageAndHideLoadingView()
        onSuccessWithNull?.invoke(data)
        onFinish?.invoke(true)
    }

    fun onNext(t: T) {
        data = t
        onSucess?.invoke(t)
    }

    fun onError(t: Throwable?) {
        showMessageAndHideLoadingView(t)
        onFailure?.invoke(t?.message)
        onFinish?.invoke(false)
    }

    private fun showMessageAndHideLoadingView(e: Throwable? = null) {
        val error = parseMessageFromError(e)
        if (error.isNotEmpty() && toast)
            weakReference.get()?.showErrorView(error)
        if (showLoading)
            weakReference.get()?.hideLoadingView()
    }

    private fun parseMessageFromError(e: Throwable?): String {
        return when (e) {
            null -> ""
            is ConnectException -> "服务器连接错误，请稍后重试。"
            is SocketTimeoutException -> "服务器连接超时，请稍后重试。"
            else -> {
                e.printStackTrace()
                e.message ?: "未知错误，请稍后重试"
            }
        }
    }
}