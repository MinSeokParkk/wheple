package com.minseok.wheple.map

import android.content.Context
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MapContract {

    interface View : BaseView<Presenter> {
        fun loc_setting(x:Double, y:Double)
        fun showToast(string: String)

    }

    interface Presenter : BasePresenter {
        fun setAddr(addr:String, context: Context)

    }
}