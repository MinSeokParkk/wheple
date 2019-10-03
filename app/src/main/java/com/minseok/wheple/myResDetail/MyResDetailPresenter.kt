package com.minseok.wheple.myResDetail

import com.minseok.wheple.CheckReservationTime
import com.minseok.wheple.place.PlaceDetailItem
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyResDetailPresenter (private val view : MyResDetailContract.View): MyResDetailContract.Presenter {


    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var comparetime = CheckReservationTime()
    var placeNo =""

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun bringData(no: String) {
        var sending = "{ \"no\" : \""+ no + "\"}"


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

        view.setData(myd.place, myd.photo,myd.date, myd.time, myd.name, myd.phone, myd.price, myd.usedpoint, myd.payment,
                     myd.refund_price, myd.refund_point)

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
