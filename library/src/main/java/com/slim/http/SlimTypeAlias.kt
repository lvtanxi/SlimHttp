package com.slim.http

/**
 *  访问成功后的回调
 * @author melon
 * @version 1.0
 * @since JDK1.8
 */

typealias Finish = ((isSuccess: Boolean) -> Unit)?

typealias Sucess<T> = ((it: T) -> Unit)?

typealias SuccessWithNull<T> = ((it: T?) -> Unit)?

typealias Failure = ((errorMessage: String?) -> Unit)?

