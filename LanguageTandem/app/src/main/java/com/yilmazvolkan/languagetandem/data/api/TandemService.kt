package com.yilmazvolkan.languagetandem.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TandemService {

    @GET("community_{page}.json")
    suspend fun getTandemCommunity(@Path("page") page: Int): Response<TandemResult>
}