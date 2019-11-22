package com.minseok.wheple.addcoupon

import com.minseok.wheple.mycoupon.MycouponActivity
import com.minseok.wheple.myinfo.MyinfoActivity
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddcouponPresenter (private val view : AddcouponContract.View): AddcouponContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }

    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun changeCoupon(coupon: String) {
        if(coupon.contains(" ")){
            view.setCoupon(coupon.replace(" ",""))
        }

        if(coupon.trim().length>0){
            view.buttonOn()
        }else{
            view.alarmOn("쿠폰 코드를 입력해주세요.")
        }
    }

    override fun saveCoupon(coupon: String) {
        val sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"coupon\" : \""+coupon+"\"}"
        disposable =
            apiService.connect_server("mycoupon_add.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult(result) }
                )
    }

    fun showResult(result: Result.Connectresult){
        println("return ======"+result.result )
        if(result.result.equals("1")) {
            view.alarmOn("유효하지 않은 쿠폰입니다. 쿠폰코드를 다시 확인해주세요.")
            view.buttonOff()
            view.wrongInput()
        }else if(result.result.equals("2")){
            view.alarmOn("이미 사용한 쿠폰입니다.")
            view.buttonOff()
            view.wrongInput()
        } else {
            view.showToast("쿠폰이 등록되었습니다.")

            MycouponActivity.change = true
            view.back()
        }
    }

}