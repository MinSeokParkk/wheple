package com.minseok.wheple.retrofit


import android.content.res.Resources
import android.provider.Settings.Secure.getString
import android.util.Log
import com.minseok.wheple.R
import com.minseok.wheple.shared.App
import io.reactivex.Observable

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import kotlin.coroutines.coroutineContext


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
            val baseurl:String = App().getContext().resources.getString(R.string.base_url)

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseurl)
                .build()


            return retrofit.create(APIService::class.java)
        }
    }

}