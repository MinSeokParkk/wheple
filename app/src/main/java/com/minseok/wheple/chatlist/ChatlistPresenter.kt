package com.minseok.wheple.chatlist

import com.minseok.wheple.shared.App

class ChatlistPresenter (private val view : ChatlistContract.View): ChatlistContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun check_preference() {

        if(App.prefs.autologin!=""){
            view.login_mode()
        }else{
            view.guest_mode()
        }
    }
}