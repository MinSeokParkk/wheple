package com.minseok.wheple.login

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun loginSuccess()
        fun showToast(string: String)
        fun wrongInput(int: Int)
        fun loginbutton_off()
        fun loginbutton_on()

    }

    interface Presenter : BasePresenter {
        fun inputCheck(email: String, password: String){

        }
        fun login(email: String, password: String) {
        }
    }

}