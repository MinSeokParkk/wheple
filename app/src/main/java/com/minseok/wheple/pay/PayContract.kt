package com.minseok.wheple.pay

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface PayContract {
    interface View : BaseView<Presenter> {
        fun webviewLoadUrl(resultUrl:String)
        fun resSave_success()
    }

    interface Presenter : BasePresenter {
        fun bringKakaoPay(baseurl:String, adminkey:String, placename:String, payment:String)
        fun reserve(date:String, time:String, place:String, time_text: String, name:String,
                     phone:String, price:String, payment:String, usedpoint:String, coupon:String)

    }
}