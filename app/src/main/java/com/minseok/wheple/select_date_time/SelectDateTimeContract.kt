package com.minseok.wheple.select_date_time

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.select_date_time.adapter.TimeAdapter

interface SelectDateTimeContract {

    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun connectAdapter()
        fun gotoNext(space:String, date:String, timeNo:String, timeText:String)
        fun gotoLogin(space:String, date:String, timeNo:String, timeText:String)

    }


    interface Presenter : BasePresenter {
        fun sendDate(no:String, date:String, timeAdapter:TimeAdapter)
        fun login_check(space:String, date:String, timeNo:String, timeText:String)
    }
}