package com.yilmazvolkan.languagetandem.repository

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
    private var isLastPage = false

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TandemData>
    ) {
        load(1) { before, next, response -> callback.onResult(response, before, next) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TandemData>) {
        load(params.key) { _, next, response -> callback.onResult(response, next) }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TandemData>
    ) {
    }

    private fun load(
        page: Int,
        callback: (before: Int?, next: Int?, result: List<TandemData>) -> Unit
    ) {
        updateState(Status.LOADING)
        compositeDisposable.add(
            tandemRepository.getTandems(page)
                .subscribe(
                    { response ->
                        if (response.response.isEmpty()) {
                            updateState(Status.EMPTY)
                        } else {
                            if (response.response.size < PAGE_SIZE)
                                isLastPage = true
                            updateState(Status.SUCCESS)
                            callback(
                                if (page == 0) null else page - 1,
                                if (response.response.size < PAGE_SIZE) null else page + 1,
                                response.response
                            )
                        }
                    }
                ) {
                    updateState(Status.ERROR)
                    setRetry {
                        if (isLastPage.not())
                            load(page, callback)
                    }
                }
        )
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

    companion object {
        const val PAGE_SIZE = 20
    }
}