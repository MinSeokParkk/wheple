package com.minseok.wheple.login

import com.minseok.wheple.BasePresenter
import com.minseok.wheple.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun loginSuccess()
        fun showToast(string: String)
        fun wrongInput(int: Int)
        fun loginbutton(email: Boolean, password: Boolean)

    }

    interface Presenter : BasePresenter {
        fun inputCheck(email: String, password: String){

        }
        fun login(email: String, password: String) {
        }
    }

}