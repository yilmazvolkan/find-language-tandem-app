package com.yilmazvolkan.languagetandem.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.yilmazvolkan.languagetandem.models.Resource
import com.yilmazvolkan.languagetandem.models.Status
import kotlinx.coroutines.Dispatchers

/**
 * Using Kotlin Coroutines and LiveData, we are launching a new IO coroutine.
 * Thus, we can use our suspend functions, and store our results in a LiveData.
 */
fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }