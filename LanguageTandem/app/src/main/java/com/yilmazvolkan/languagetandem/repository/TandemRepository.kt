package com.yilmazvolkan.languagetandem.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yilmazvolkan.languagetandem.data.api.TandemApi
import com.yilmazvolkan.languagetandem.data.api.TandemResult
import com.yilmazvolkan.languagetandem.data.database.DataDao
import com.yilmazvolkan.languagetandem.data.database.toDataEntityList
import com.yilmazvolkan.languagetandem.data.database.toDataList
import com.yilmazvolkan.languagetandem.di.DaggerAppComponent
import com.yilmazvolkan.languagetandem.models.TandemData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class TandemRepository {

    @Inject
    lateinit var tandemApiService: TandemApi

    @Inject
    lateinit var tandemDao: DataDao

    val tandemData by lazy { MutableLiveData<List<TandemData>>() }
    val isInProgress by lazy { MutableLiveData<Boolean>() }
    val isError by lazy { MutableLiveData<Boolean>() }

    init {
       DaggerAppComponent.create().inject(this)
    }

    private fun insertData(): Disposable {
        return tandemApiService.getTandemCommunity(1)
                .subscribeOn(Schedulers.io())
                .subscribeWith(subscribeToDatabase())
    }

    private fun subscribeToDatabase(): DisposableSubscriber<TandemResult> {
        return object : DisposableSubscriber<TandemResult>() {

            override fun onNext(tandemResult: TandemResult?) {
                if (tandemResult != null) {
                    val entityList = tandemResult.response.toList().toDataEntityList()
                    tandemDao.insertData(entityList)
                }
            }

            override fun onError(t: Throwable?) {
                isInProgress.postValue(true)
                Log.e("InsertData", "TandemResult error: ${t?.message}")
                isError.postValue(true)
                isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("InsertData", "insert success")
                getTandemCommunityQuery()
            }
        }
    }

    private fun getTandemCommunityQuery(): Disposable {
        return tandemDao
                .queryData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataEntityList ->
                            isInProgress.postValue(true)
                            if (dataEntityList != null && dataEntityList.isNotEmpty()) {
                                isError.postValue(false)
                                tandemData.postValue(dataEntityList.toDataList())
                                Log.d("Load", "Loaded from database.")
                            } else {
                                insertData()
                            }
                            isInProgress.postValue(false)
                        },
                        {
                            isInProgress.postValue(true)
                            Log.e("TandemQuery", "Database error: ${it.message}")
                            isError.postValue(true)
                            isInProgress.postValue(false)
                        }
                )
    }


    fun fetchDataFromDatabase(): Disposable = getTandemCommunityQuery()
}