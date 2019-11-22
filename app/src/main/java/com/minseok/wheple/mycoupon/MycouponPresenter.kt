package com.minseok.wheple.mycoupon

import android.util.Log
import com.minseok.wheple.mycoupon.adapter.MycouponAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MycouponPresenter (private val view : MycouponContract.View): MycouponContract.Presenter{

    lateinit var myAdapter:MycouponAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override  fun getCoupon(cAdapter: MycouponAdapter){
        myAdapter = cAdapter
        val sending =  "{ \"email\" : \""+ App.prefs.autologin +"\"}"
        disposable =
            apiService.connect_server("mycoupon.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mycou -> showResult(mycou) }
                )
    }

    fun showResult(mycou: Result.Connectresult){
        Log.d("MycouponA1", mycou.mycou.toString())
        myAdapter.addItems(mycou.mycou)
        myAdapter.notifyAdapter()
        view.connectAdapter()

        var couNum = "0"
        if(mycou.mycou.size>0){
            couNum = mycou.mycou[0].couponNum
        }else{
            view.nocoupon()
        }
        view.setNum(couNum)

    }
}