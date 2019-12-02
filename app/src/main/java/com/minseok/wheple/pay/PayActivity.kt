package com.minseok.wheple.pay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.ReservationSuccessActivity
import com.minseok.wheple.pay.model.KakaoApi
import com.minseok.wheple.pay.model.KakaoResult
import com.minseok.wheple.reservation.ReservationActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment.*

class PayActivity:AppCompatActivity(), PayContract.View{

    companion object{
        var activity: Activity? = null
    }

    private lateinit var mPresenter: PayContract.Presenter



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        activity = this@PayActivity

        PayPresenter(this)


        webviewpayment.settings.javaScriptEnabled = true
        webviewpayment.addJavascriptInterface(WebAppInterface(this), "Android")
        webviewpayment.webViewClient = CustomWebViewClient()

        mPresenter.bringKakaoPay(get_base_url(), getString(R.string.kakao_api_key),
            intent.getStringExtra("placename"), intent.getStringExtra("payment"))



    }





    override fun setPresenter(presenter: PayContract.Presenter) {
        this.mPresenter = presenter
    }

    fun get_base_url() :String{
        return getString(R.string.base_url)
    }

    override fun webviewLoadUrl(resultUrl:String){
        webviewpayment.loadUrl(resultUrl)
    }


    inner class CustomWebViewClient : WebViewClient(){

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url!!.startsWith(get_base_url())){
                return super.shouldOverrideUrlLoading(view, url)
            }else{
                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                val existPackage = packageManager.getLaunchIntentForPackage(intent.`package`)
                if (existPackage != null) {
                    startActivity(intent)
                }
                return true
            }

        }
    }

    inner class WebAppInterface(context:Context){
         var mContext:Context  = context


        @JavascriptInterface
        fun cancelpay(toast:String){
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
           activity?.finish()
        }

        @JavascriptInterface
        fun paysuccess(){

            mPresenter.reserve(intent.getStringExtra("date"), intent.getStringExtra("time"),
                         intent.getStringExtra("place"), intent.getStringExtra("time_text"),
                         intent.getStringExtra("name"), intent.getStringExtra("phone"),
                         intent.getStringExtra("price"), intent.getStringExtra("payment"),
                         intent.getStringExtra("usedpoint"), intent.getStringExtra("coupon"))

//            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
//
//            val nextIntent = Intent(mContext, ReservationSuccessActivity::class.java)
//            startActivity(nextIntent)
//            activity?.finish()
//            ReservationActivity.activity?.finish()
        }

    }

    override fun resSave_success(){
            Toast.makeText(this, "예약 완료", Toast.LENGTH_SHORT).show()

            val nextIntent = Intent(this, ReservationSuccessActivity::class.java)
            startActivity(nextIntent)
            activity?.finish()
            ReservationActivity.activity?.finish()
    }





}