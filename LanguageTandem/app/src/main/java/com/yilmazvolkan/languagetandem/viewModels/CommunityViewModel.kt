package com.yilmazvolkan.languagetandem.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yilmazvolkan.languagetandem.models.Status
import com.yilmazvolkan.languagetandem.models.TandemData
import com.yilmazvolkan.languagetandem.repository.CommunityDataSource
import com.yilmazvolkan.languagetandem.repository.CommunityDataSourceFactory
import io.reactivex.disposables.CompositeDisposable


class CommunityViewModel @ViewModelInject constructor(
    private val communityDataSourceFactory: CommunityDataSourceFactory,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private var tandemList: LiveData<PagedList<TandemData>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        tandemList = LivePagedListBuilder(communityDataSourceFactory, config).build()
    }

    fun getState(): LiveData<Status> = Transformations.switchMap(
        communityDataSourceFactory.tandemsDataSourceLiveData,
        CommunityDataSource::state
    )

    fun retry() {
        communityDataSourceFactory.tandemsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return tandemList.value?.isEmpty() ?: true
    }

    fun getTandemList() = tandemList

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
