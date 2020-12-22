package com.yilmazvolkan.languagetandem.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yilmazvolkan.languagetandem.models.TandemData
import io.reactivex.disposables.CompositeDisposable

class CommunityDataSourceFactory(
    private val repository: TandemRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, TandemData>() {

    val tandemsDataSourceLiveData = MutableLiveData<CommunityDataSource>()

    override fun create(): DataSource<Int, TandemData> {
        val newsDataSource = CommunityDataSource(repository, compositeDisposable)
        tandemsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}