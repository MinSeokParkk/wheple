package com.minseok.wheple.signup_others

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface SignupOthersContract {

    interface View : BaseView<Presenter> {
        fun signupSuccess()
        fun showToast(string: String)
        fun wrongInput(int: Int)
        fun signupbutton_on()
        fun signupbutton_off()
   }

    interface Presenter : BasePresenter {
        fun signup(email: String, password: String, repassword: String, nickname: String, phone: String, agreement: Boolean) {
        }

        fun inputCheck(email: String, password: String, repassword: String, nickname: String, agreement: Boolean){

        }
    }

}