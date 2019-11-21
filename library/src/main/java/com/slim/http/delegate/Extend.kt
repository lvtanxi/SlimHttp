package com.slim.http.delegate

import androidx.lifecycle.LifecycleOwner
import com.slim.http.SlimSubscriber
import com.slim.http.intes.PageWidgetInterface
import com.slim.http.intes.WidgetInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *各种扩展
 *
 * @author melon
 * @version 1.0
 * @since JDK1.8
 */

fun <T> Deferred<T>.then(block: (T) -> Unit) {
    GlobalScope.launch {
        block(this@then.await())
    }
}

fun <T> LifecycleOwner.load(loader: () -> Deferred<T>): Deferred<T> {
    val job = GlobalScope.async {
        loader().await()
    }
    lifecycle.addObserver(CoroutineLifecycleDeleagte(job))
    return job
}


fun <T> LifecycleOwner.slim(
    widgetInterface: WidgetInterface,
    showLoading: Boolean = true,
    toast: Boolean = true,
    loader: () -> Deferred<T>
): SlimSubscriber<T> {
    val simpleSubscriber = SlimSubscriber<T>(widgetInterface, showLoading, toast)
    simpleSubscriber.onStart()
    GlobalScope.launch(Main) {
        try {
            val job = load(loader)
            val result = job.await()
            simpleSubscriber.onNext(result)
        } catch (e: Exception) {
            simpleSubscriber.onError(e)
        } finally {
            simpleSubscriber.onComplete()
        }
    }
    return simpleSubscriber
}


fun <T> LifecycleOwner.page(widgetInterface: WidgetInterface, loader: () -> Deferred<T>): SlimSubscriber<T> {
    return slim(widgetInterface, false, true, loader)
        .onSuccessWithNull {
            if (widgetInterface is PageWidgetInterface)
                widgetInterface.addItems(it)
        }
        .onFinish {
            if (widgetInterface is PageWidgetInterface)
                widgetInterface.stopRefresh(it)
        }
}

fun LifecycleOwner.zip(
    widgetInterface: WidgetInterface,
    showLoading: Boolean = true,
    toast: Boolean = true,
    deferreds: () -> MutableList<Deferred<Any>>
): SlimSubscriber<LinkedHashMap<Int, Any>> {
    val simpleSubscriber = SlimSubscriber<LinkedHashMap<Int, Any>>(widgetInterface, showLoading, toast)
    simpleSubscriber.onStart()
    GlobalScope.launch(Main) {
        try {
            val results = LinkedHashMap<Int, Any>()
            val target = deferreds.invoke()
            for (item in target.withIndex()) {
                val job = load { item.value }
                results[item.index] = job.await()
            }
            if (results.size == target.size) {
                simpleSubscriber.onNext(results)
            }
        } catch (e: Exception) {
            simpleSubscriber.onError(e)
        } finally {
            simpleSubscriber.onComplete()
        }
    }
    return simpleSubscriber
}








