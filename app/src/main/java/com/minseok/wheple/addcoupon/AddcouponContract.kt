package com.minseok.wheple.addcoupon

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface AddcouponContract {

    interface View : BaseView<Presenter> {
        fun alarmOn(string:String)
        fun setCoupon(coupon:String)
        fun buttonOn()
        fun buttonOff()
        fun back()
        fun showToast(string: String)
        fun wrongInput()

    }

    interface Presenter : BasePresenter {
        fun changeCoupon(coupon: String)
        fun saveCoupon(coupon: String)

    }
}