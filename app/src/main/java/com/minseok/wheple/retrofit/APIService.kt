package com.minseok.wheple.retrofit

import android.provider.Settings.Secure.getString
import com.minseok.wheple.R
import io.reactivex.Observable
import okhttp3.HttpUrl
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface APIService {



    @FormUrlEncoded
    @POST
     fun connect_server(
        @Url url: String,
        @Field("sending") sending: String
     ): Observable<Result.Connectresult>

    @Multipart
    @POST
    fun post_Porfile_Request(
        @Url url: String,
        @Part("sending") sending: String,
        @Part imageFile : ArrayList<MultipartBody.Part>
    ): Observable<Result.Connectresult>


    companion object {

        fun create(): APIService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.30.1.27:9999/")
                .build()

            return retrofit.create(APIService::class.java)
        }
    }

}