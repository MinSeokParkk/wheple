package com.minseok.wheple.retrofit

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url


interface APIService {

    @FormUrlEncoded
    @POST
     fun connect_server(
        @Url url: String,
        @Field("sending") sending: String
     ): Observable<Result.Connectresult>


    companion object {
        fun create(): APIService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.30.1.8:9999/")
                .build()

            return retrofit.create(APIService::class.java)
        }
    }

}