package com.minseok.wheple.signup_others

import com.minseok.wheple.BasePresenter
import com.minseok.wheple.BaseView
import javax.crypto.KeyAgreement

interface SignupOthersContract {

    interface View : BaseView<Presenter> {
        fun signupSuccess()
        fun showToast(string: String)
        fun wrongInput(int: Int)
        fun signupbutton(email: Boolean, password: Boolean, repassword: Boolean, nickname: Boolean, agreement: Boolean)
   }

    interface Presenter : BasePresenter {
        fun signup(email: String, password: String, repassword: String, nickname: String, phone: String) {
        }

        fun inputCheck(email: String, password: String, repassword: String, nickname: String, agreement: Boolean){

        }
    }

}