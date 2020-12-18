package com.yilmazvolkan.languagetandem.data.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
interface TandemApi {

    @GET("community_{page}.json")
    fun getTandemCommunity(
        @Path("page") page: Int
    ): Flowable<TandemResult>
}