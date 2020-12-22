package com.yilmazvolkan.languagetandem.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yilmazvolkan.languagetandem.models.Status
import com.yilmazvolkan.languagetandem.models.TandemData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CommunityDataSource(
    private val tandemRepository: TandemRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, TandemData>() {

    var state: MutableLiveData<Status> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TandemData>
    ) {
        updateState(Status.LOADING)
        compositeDisposable.add(
            tandemRepository.getTandems(1)
                .subscribe(
                    { response ->
                        if (response.response.isEmpty()) {
                            updateState(Status.ERROR)
                        } else {
                            updateState(Status.SUCCESS)
                            callback.onResult(
                                response.response,
                                null,
                                2
                            )
                        }
                    }
                ) {
                    updateState(Status.ERROR)
                    setRetry { loadInitial(params, callback) }
                }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TandemData>) {
        updateState(Status.LOADING)
        compositeDisposable.add(
            tandemRepository.getTandems(params.key)
                .subscribe(
                    { response ->
                        if (response.response.isEmpty()) {
                            updateState(Status.ERROR)
                        } else {
                            updateState(Status.SUCCESS)
                            callback.onResult(
                                response.response,
                                params.key + 1
                            )
                        }

                    }
                ) {
                    updateState(Status.ERROR)
                    setRetry { loadAfter(params, callback) }
                }
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TandemData>
    ) {
    }

    private fun updateState(status: Status) {
        state.postValue(status)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}