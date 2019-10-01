package com.minseok.wheple.select_date_time

import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.select_date_time.adapter.TimeAdapter
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class SelectDateTimePresenter (private val view: SelectDateTimeContract.View): SelectDateTimeContract.Presenter{

    lateinit var timeAdapter1 :TimeAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }


    override fun sendDate(no: String, date: String, timeAdapter: TimeAdapter) {

        timeAdapter1 = timeAdapter

        var sending : String
        sending = "{ \"no\" : \""+ no +"\", \r\n" +
                "\"date\" : \""+date+"\"}"
        println("sending =====" + sending)

        disposable =
            apiService.connect_server("select.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { date -> showResult(date) }
                )
    }


    fun showResult(date: Result.Connectresult){

        //기본 시간대
        val time = date.date.time
        val strs = time.split("|").toTypedArray()
        var categoryList : ArrayList<String>?=null
        categoryList = strs.toCollection(ArrayList())

        //예약 된 시간대
        val reserve = date.date.reservation
        val str = reserve.split("|").toTypedArray()
        var aList : ArrayList<String>?=null
        aList =  str.toCollection(ArrayList())

//        //예약 진행 중인 시간대
//        val ing = date.date.temp
//        println("예약 임시" + ing)
//        val str_ing = ing.split("|").toTypedArray()
//        var ing_List : ArrayList<String>?=null
//        ing_List =  str_ing.toCollection(ArrayList())

        timeAdapter1.addItems(categoryList, aList)
        timeAdapter1.notifyAdapter()

     view.connectAdapter()


    }

    override fun login_check(space:String,date:String,  timeNo:String, timeText:String) {
        if(App.prefs.autologin!=""){
            view.gotoNext(space, date, timeNo, timeText)
        }else{
            view.gotoLogin(space, date, timeNo, timeText)
        }
    }


}