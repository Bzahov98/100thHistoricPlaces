package com.tu.pmu.the100th.internal.utils

import kotlinx.coroutines.*

import android.util.Log
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

private const val TAG = "asDefered"
fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()
    this.addOnSuccessListener {
        Log.d(TAG, " is successful")
        deferred.complete(it)
    }
    this.addOnFailureListener {

        Log.d(TAG, " failed")
        deferred.completeExceptionally(it)
    }
    return deferred
}