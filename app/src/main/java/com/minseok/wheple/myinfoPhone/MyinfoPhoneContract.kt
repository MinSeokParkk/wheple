package com.minseok.wheple.myinfoPhone

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MyinfoPhoneContract {

    interface View : BaseView<Presenter> {
        fun deleteOn()
        fun deleteOff()
        fun alarmOn(string:String)
        fun buttonOn()
        fun buttonOff()
        fun wrongInput()
        fun showToast(string: String)
        fun back()

    }

    interface Presenter : BasePresenter {
        fun changePhone(phone:String, oldPhone:String)
        fun savePhone(phone: String)

    }
}