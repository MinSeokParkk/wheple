package com.minseok.wheple.pay.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface KakaoApi {




    @Headers("Authorization: KakaoAK 6ccefc43ce7b96e952c0e962227de332")
    @POST("v1/payment/ready")
    fun connect_pay(
        @Query("cid") cid: String,
        @Query("partner_order_id") partner_order_id:String,
        @Query("partner_user_id") partner_user_id:String,
        @Query("item_name") item_name:String,
        @Query("quantity") quantity:Int,
        @Query("total_amount") total_amount:Int,
        @Query("tax_free_amount") tax_free_amount:Int,
        @Query("approval_url") approval_url:String,
        @Query("fail_url") fail_url:String,
        @Query("cancel_url") cancel_url:String


    ): Observable<KakaoResult.KakaoConnectResult>



    companion object {



        fun create(): KakaoApi {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://kapi.kakao.com/")
                .build()

            return retrofit.create(KakaoApi::class.java)
        }
    }
}