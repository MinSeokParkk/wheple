package com.minseok.wheple.myinfoName

import android.net.Uri
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import java.io.File

interface MyinfoNameContract {

    interface View : BaseView<Presenter> {
        fun deleteOn()
        fun deleteOff()
        fun alarmOn(string:String)
        fun buttonOn()
        fun buttonOff()
        fun showToast(string: String)
        fun back()
        fun setName(name:String)

    }

    interface Presenter : BasePresenter {
        fun changeName(name:String, oldName:String)
        fun saveName(name: String)

    }
}