package com.minseok.wheple.pay

import android.os.Build
import android.provider.Settings.Global.getString
import androidx.annotation.RequiresApi
import com.minseok.wheple.R
import com.minseok.wheple.pay.model.KakaoApi
import com.minseok.wheple.pay.model.KakaoResult
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment.*

class PayPresenter (private val view : PayContract.View): PayContract.Presenter{



    val kakaoapi by lazy {
        KakaoApi.create()
    }

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bringKakaoPay(baseurl:String, adminkey:String, placename:String, payment:String){

        disposable =  kakaoapi.connect_pay( "KakaoAK "+adminkey,"TC0ONETIME",
            "partner_order_id",  "partner_user_id", placename, 1, payment.toInt(),  0,
            baseurl+"ksuccess.php", baseurl+"kfail.php", baseurl+"kcancel.php"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {result -> showResult(result) },
                {err->println("ERROR 에러 에러에러에러에러에러 : "+err.toString())}
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showResult(result: KakaoResult.KakaoConnectResult){
        println("나옵니다~~~~~\n "+result.tid + "\n" + result.next_redirect_app_url + "\n" + result.android_app_scheme)

        view.webviewLoadUrl(result.next_redirect_app_url)
    }



    override fun reserve(date:String, time:String, place:String, time_text: String, name:String,
                phone:String, price:String, payment:String, usedpoint:String, coupon:String){

        var sending : String
        sending = "{ \"date\" : \""+ date + "\", \r\n" +
                "\"time\" : \""+ time + "\", \r\n" +
                "\"place\" : \""+ place + "\", \r\n" +
                "\"time_text\" : \""+ time_text + "\", \r\n" +
                "\"email\" : \""+ App.prefs.autologin + "\", \r\n" +
                "\"name\" : \""+ name + "\", \r\n" +
                "\"phone\" : \""+ phone + "\", \r\n" +
                "\"price\" : \""+ price + "\", \r\n" +
                "\"payment\" : \""+ payment + "\", \r\n" +
                "\"coupon\" : \""+ coupon + "\", \r\n" +
                "\"usedpoint\" : \""+usedpoint+"\"}"

        println(sending)

        disposable =
            apiService.connect_server("reservation.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult_Second(result) }
                )
    }

    fun showResult_Second(result: Result.Connectresult){
        if(result.result.equals("0")){
            view.resSave_success()



        } else {
//            view.showToast("예약 실패")
        }

    }

}