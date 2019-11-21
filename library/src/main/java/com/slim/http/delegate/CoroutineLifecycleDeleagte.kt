package com.slim.http.delegate

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.Deferred

/**
 *监听声明周期
 *
 * @author melon
 * @version 1.0
 * @since JDK1.8
 */
class CoroutineLifecycleDeleagte(private val job: Deferred<*>) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() = handleEvent()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = handleEvent()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() = handleEvent()

    private fun handleEvent() {
        if (!job.isCancelled) {
            job.cancel()
        }
    }

}