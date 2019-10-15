package com.minseok.wheple.mypage

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MypageContract {

    interface View : BaseView<Presenter>{
        fun login_mode()
        fun guest_mode()
        fun set_myinfo(nickname:String, point:String, photo:String)
    }

    interface Presenter : BasePresenter{
        fun check_preference()
        fun logout()
    }

}