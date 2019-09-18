package com.minseok.wheple.mypage

import com.minseok.wheple.shared.App

class MypagePresenter (private val view: MypageContract.View): MypageContract.Presenter{
    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun check_preference() {
        if(App.prefs.autologin){
            view.login_mode()
        }else{
            view.guest_mode()
        }
    }
}