package com.minseok.wheple.cancel

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface CancelContract {

    interface View : BaseView<Presenter> {
        fun setText(refund:String, repoint:String, price:String, usedpoint:String, payment:String,
                    usedcoupon:String, returncoupon:String)
        fun button_on()
        fun button_off()
        fun showToast(string: String)
        fun back()

    }

    interface Presenter : BasePresenter {
        fun setData(no:String)
        fun checkchange(check:Boolean)
        fun clickbutton(check:Boolean, no:String, refund:String, repoint: String, recoupon:String)
    }
}