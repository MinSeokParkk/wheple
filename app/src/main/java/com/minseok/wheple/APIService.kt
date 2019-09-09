package com.minseok.wheple

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface APIService {

    @FormUrlEncoded
    @POST("login.php")
     fun serverlogin(
        @Field("email") email: String,
        @Field("password") password:String
     ): Observable<Result.Loginresult>

    @FormUrlEncoded
    @POST("phonecheck.php")
    fun serverphonecheck(
        @Field("phone") phone: String
     ): Observable<Result.Phoneresult>

    @FormUrlEncoded
    @POST("signup.php")
    fun serversignup(
        @Field("email") email: String,
        @Field("password") password:String,
        @Field("nickname") nickname:String,
        @Field("phone") phone:String
     ): Observable<Result.Signupresult>


    companion object {
        fun create(): APIService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.30.1.20:9999/")
                .build()

            return retrofit.create(APIService::class.java)
        }
    }

}