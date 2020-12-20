package com.yilmazvolkan.languagetandem.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TandemService {

    @GET("community_{page}.json")
    fun getTandemCommunity(@Path("page") page: Int): Single<ResponseResult>
}