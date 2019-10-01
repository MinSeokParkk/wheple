package com.minseok.wheple.reservation

import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.select_date_time.SelectDateTimeActivity
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*

class ReservationPresenter (private val view : ReservationContract.View): ReservationContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    val regex = ReservationRegex()

    val point_Cal = Point_Calculator()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

//    override fun temp_reserve(space: String, date: String, timeNo: String) {
//                    var sending : String
//            sending = "{ \"place\" : \""+ space + "\", \r\n" +
//                        "\"date\" : \""+ date + "\", \r\n" +
//                        "\"time\" : \""+timeNo+"\"}"
//
//            disposable =
//                apiService.connect_server("add_temp.php", sending)
//
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        { result ->  }
//                    )
//    }
//
//    override fun delete_temp(space: String, date: String, timeNo: String) {
//        var sending : String
//        sending = "{ \"place\" : \""+ space + "\", \r\n" +
//                "\"date\" : \""+ date + "\", \r\n" +
//                "\"time\" : \""+timeNo+"\"}"
//
//        disposable =
//            apiService.connect_server("delete_temp.php", sending)
//
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { result ->  }
//                )
//    }

    override fun showDetail(space: String,date:String, timeNo: String, timeText: String) {

        val user = App.prefs.autologin

        var sending = "{ \"no\" : \""+ space + "\", \r\n" +
                "\"user\" : \""+user+"\"}"
        disposable =
            apiService.connect_server("res_before.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res -> showResult(res, date, timeText) }
                )
    }

    fun showResult(res: Result.Connectresult, date: String, timeText: String){

        var re : ReservationItem
        re = res.res

        var totalprice = re.hour.toInt()*re.price.toInt()

        view.setRes(re.name, date, timeText, NumberFormat.getNumberInstance(Locale.US).format(re.price.toInt()), re.hour,
            NumberFormat.getNumberInstance(Locale.US).format(totalprice), re.phone,re.point)

    }

    override fun checkchange(check: Boolean) {
        view.otherscheck(check)
    }

    override fun useAllPoint(string: String) {
        val allPoint = string.split("P").toTypedArray()
        view.writePoint(allPoint[0])
    }

    override fun inputPointCheck(price:String, point: String, mypoint:String) {

        val message= point_Cal.point_check(point, mypoint)
        if(!message.equals("stop")) {
            view.writePoint(message)
        }
        view.setPrice(point_Cal.point_cal(price, point, mypoint))
    }

    override  fun inputCheck(name:String, phone:String, useAgree :Boolean, cancelAgree:Boolean, personalAgree:Boolean){
        if(regex.lengthCheck(name) && regex.lengthCheck(phone) && useAgree && cancelAgree && personalAgree){
            view.paybutton_on()
        }else{
            view.paybutton_off()
        }
    }

    override fun reserve_check(name:String, phone:String, useAgree :Boolean, cancelAgree:Boolean, personalAgree:Boolean,
                         date:String, time:String, place:String, time_text:String, price:String, payment:String, usedpoint:String) {
        if (!regex.lengthCheck(name)) {
            view.showToast("예약자 이름을 입력해주세요.")
            view.wrongInput(1)
        } else if (!regex.lengthCheck(phone)) {
            view.showToast("휴대폰 번호를 입력해주세요.")
            view.wrongInput(2)
        }else if(!regex.phonecheck(phone)){
            view.showToast("휴대폰 번호 형식을 다시 확인해주세요.")
            view.wrongInput(2)
        }else if(!useAgree){
            view.showToast("숙소 이용규칙에 동의해주세요.")
            view.wrongInput(3)
        }else if(!cancelAgree){
            view.showToast("취소 규정에 동의해주세요.")
            view.wrongInput(3)
        }else if(!personalAgree){
            view.showToast("개인정보 제3자 제공에 동의해주세요.")
            view.wrongInput(3)
        }else{
            var sending : String
            sending = "{ \"place\" : \""+ place + "\", \r\n" +
                        "\"date\" : \""+ date + "\", \r\n" +
                        "\"time\" : \""+time+"\"}"

            disposable =
                apiService.connect_server("reservation_doublecheck.php", sending)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult_First(result, date, time, place, time_text, name, phone, price, payment, usedpoint) }
                    )




        }
    }

    fun showResult_First(result: Result.Connectresult, date:String, time:String, place:String, time_text: String, name:String, phone:String, price:String, payment:String, usedpoint:String){
        if(result.result.equals("success")){


            val email = App.prefs.autologin

            var pricechange = price.replace(",", "")
            pricechange = pricechange.replace("원", "")

            var paymentchange = payment.replace(",", "")
            paymentchange = paymentchange.replace("원", "")

            var usedPoint = usedpoint
            if(usedpoint.equals("")){
                usedPoint = "0"
            }

            reserve(date, time, place, time_text, email, name, phone, pricechange, paymentchange, usedPoint)


        } else if(result.result.equals("fail")){
//            view.showToast("다른 사람이 이미 예약한 시간입니다.\n다른 시간대를 선택해주세요.")
//            SelectDateTimeActivity.MyClass.activity?.finish()
//            view.gotoback()
            view.lateReserve()
        }

    }

    fun reserve(date:String, time:String, place:String, time_text: String, email:String, name:String,
                phone:String, price:String, payment:String, usedpoint:String){

        var sending : String
        sending = "{ \"date\" : \""+ date + "\", \r\n" +
                "\"time\" : \""+ time + "\", \r\n" +
                "\"place\" : \""+ place + "\", \r\n" +
                "\"time_text\" : \""+ time_text + "\", \r\n" +
                "\"email\" : \""+ email + "\", \r\n" +
                "\"name\" : \""+ name + "\", \r\n" +
                "\"phone\" : \""+ phone + "\", \r\n" +
                "\"price\" : \""+ price + "\", \r\n" +
                "\"payment\" : \""+ payment + "\", \r\n" +
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

            view.showToast("예약 성공")
            view.gotoReservationSuccess()


        } else {
            view.showToast("예약 실패")
        }

    }




}