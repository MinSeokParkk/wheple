package com.minseok.wheple.myResDetail

import com.minseok.wheple.CheckReservationTime
import com.minseok.wheple.PhoneChange
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyResDetailPresenter (private val view : MyResDetailContract.View): MyResDetailContract.Presenter {


    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var comparetime = CheckReservationTime()
    var rembers = RememberSpace()
    var placeNo =""

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun rememeberNo(no: String){
        rembers.remember(no)
    }

    override fun bringData() {
        var sending = "{ \"no\" : \""+ rembers.putspace() + "\"}"


        disposable =
            apiService.connect_server("myreservation_detail.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { myres_de -> showResult(myres_de) }
                )
    }

    fun showResult(myres_de: Result.Connectresult){
        println("return ======"+myres_de.myres_de )

        var myd = myres_de.myres_de
        placeNo = myd.placeNo

       val phone =  PhoneChange().check(myd.phone)

        view.setData(myd.place, myd.photo,myd.date, myd.time, myd.name, phone, myd.price, myd.usedpoint, myd.payment,
                     myd.refund_price, myd.refund_point, myd.usedcoupon, myd.refund_coupon)

        if (myd.cancel.equals("t")) {
            view.cancel()
        }else if(comparetime.comparetime(myd.date, myd.time).equals("now")){
           view.using_used("이용중")
         }else if(comparetime.comparetime(myd.date, myd.time).equals("after")){
            view.using_used("이용완료")
        }

    }

    override fun settingExtra(){
        view.gotoPlace(placeNo)
    }


}
