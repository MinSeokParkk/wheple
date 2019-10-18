package com.minseok.wheple.myinfoNickname

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MyinfoNicknameContract {
    interface View : BaseView<Presenter> {
        fun deleteOn()
        fun deleteOff()
        fun alarmOn(string:String)
        fun setNickname(nickname: String)
        fun buttonOn()
        fun buttonOff()
        fun wrongInput()
        fun showToast(string: String)
        fun back()


    }

    interface Presenter : BasePresenter {
        fun changeNickname(nickname:String, oldNickname:String)
        fun saveNickname(nickname: String)
    }
}