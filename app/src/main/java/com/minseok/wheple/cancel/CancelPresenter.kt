package com.minseok.wheple.cancel

import com.minseok.wheple.myResDetail.MyResDetailActivity
import com.minseok.wheple.myReservation.MyreservationActivity
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CancelPresenter (private val view : CancelContract.View): CancelContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }

    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun setData(no: String) {
        var sending = "{ \"no\" : \""+ no + "\"}"


        disposable =
            apiService.connect_server("settingCancel.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { cancelSet -> showResult(cancelSet) }
                )
    }

    fun showResult(cancelSet: Result.Connectresult){
        println("return ======"+cancelSet.cancelSet )

        var cs = cancelSet.cancelSet

        val fees = Cancelfee().calculateFee(cs.date, cs.price, cs.payment, cs.usedpoint)

        val array = fees.split("|").toTypedArray()

        val refund = array[0]
        val repoint = array[1]

        view.setText(refund, repoint, cs.price, cs.usedpoint, cs.payment, cs.usedcoupon, cs.returncoupon)


    }

    override fun checkchange(check: Boolean) {
        if(check){
            view.button_on()
        }else{
            view.button_off()
        }
    }

    override fun clickbutton(check: Boolean, no:String, refund:String, repoint: String, recoupon:String){
        if(!check){
            view.showToast("내용 확인에 체크해주세요.")
        }else{
            val strrefund =  refund.replace(",", "")
            val strrepoint = repoint.replace(",", "")
            val strrecoupon = recoupon.replace(",", "")
            val email = App.prefs.autologin

            println("준비완료~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + no + " | " +strrefund + " | "+strrepoint)


            var sending : String
            sending = "{ \"no\" : \""+ no + "\", \r\n" +
                     "\"refund\" : \""+ strrefund + "\", \r\n" +
                    "\"point\" : \""+ strrepoint + "\", \r\n" +
                    "\"recoupon\" : \""+ strrecoupon + "\", \r\n" +
                    "\"email\" : \""+email+"\"}"

            println(sending)

            disposable =
                apiService.connect_server("canceling.php", sending)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult_Second(result) }
                    )
            }
         }


        fun showResult_Second(result: Result.Connectresult){
            if(result.result.equals("0")){

                view.showToast("예약 취소 성공")
                MyreservationActivity.MyClass.cancel = true
                view.back()

            } else {
                view.showToast("취소 실패")
            }

        }
}