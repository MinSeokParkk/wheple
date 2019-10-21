package com.minseok.wheple.deleteAccountBefore

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface DeleteAccountBeforeContract {

    interface View : BaseView<Presenter> {
        fun alarmOn(string:String)
        fun setPw(pw:String)
        fun buttonOn()
        fun buttonOff()
        fun wrongInput()
        fun showToast(string: String)
        fun nextstage()

    }

    interface Presenter : BasePresenter {
        fun changePw(pw: String)
        fun checkPw(pw: String)
    }

}