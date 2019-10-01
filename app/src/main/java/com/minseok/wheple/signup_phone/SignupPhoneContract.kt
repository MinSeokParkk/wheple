package com.minseok.wheple.signup_phone

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface SignupPhoneContract {
    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun signupbutton_on()
        fun signupbutton_off()
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