package com.minseok.wheple.coupon

import android.util.Log
import com.minseok.wheple.coupon.adapter.CouponAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CouponPresenter (private val view : CouponContract.View): CouponContract.Presenter {

    lateinit var myAdapter : CouponAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getCoupon(price: String, cAdapter: CouponAdapter) {
        myAdapter = cAdapter
        var price = price.replace("원", "")
        price = price.replace(",", "")

        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"price\" : \""+price+"\"}"
        Log.d("couponA1","sending :" + sending)
        disposable =
            apiService.connect_server("getCoupon_res.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { cps -> showResult(cps) }
                )
    }

    fun showResult(cps: Result.Connectresult){
        Log.d("couponA1",cps.cps.toString())
        myAdapter.addItems(cps.cps)
        myAdapter.notifyAdapter()
        view.connectAdapter()

    }
}