package com.minseok.wheple.signup_phone

import com.minseok.wheple.BasePresenter
import com.minseok.wheple.BaseView

interface SignupPhoneContract {
    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun signupbutton(phone: Boolean)
        fun phonecheckSuccess(phone: String)
        fun wrongInput()
    }

    interface Presenter : BasePresenter {

        fun inputCheck(phone: String){
        }
        fun phonecheck(phone: String){

        }

    }
}