package me.bristermitten.fancyprivatemines.util

import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> CompletableFuture<T>.await(): T = suspendCoroutine {
    whenComplete { value, throwable ->
        if (throwable != null) {
            it.resumeWithException(throwable)
        } else {
            it.resume(value)
        }
    }
}
