package com.minseok.wheple.reservation

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface ReservationContract {

    interface View : BaseView<Presenter> {
        fun setRes(name:String, date: String, timeText: String, price:String, hour:String, totalprice:String, phone:String, point:String)
        fun otherscheck(check:Boolean)
        fun writePoint(point: String)
        fun setPrice(price:String)
        fun gotoReservationSuccess()
        fun paybutton_on()
        fun paybutton_off()
        fun wrongInput(int:Int)
        fun showToast(string: String)
        fun lateReserve()

    }


    interface Presenter : BasePresenter {
//        fun temp_reserve(space:String, date:String, timeNo:String)
        fun showDetail(space:String, date:String, timeNo:String, timeText:String)
        fun checkchange(check:Boolean)
        fun useAllPoint(string:String)
        fun inputPointCheck(price:String, point:String, mypoint:String)
        fun inputCheck(name:String, phone:String, useAgree :Boolean, cancelAgree:Boolean, personalAgree:Boolean)
        fun reserve_check(name:String, phone:String, useAgree :Boolean, cancelAgree:Boolean, personalAgree:Boolean,
                    date:String, time:String, place:String, time_text:String, price:String, payment:String, usedpoint:String)
//        fun delete_temp(space:String, date:String, timeNo:String)
    }
}