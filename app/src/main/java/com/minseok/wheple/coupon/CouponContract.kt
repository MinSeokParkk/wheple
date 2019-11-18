package com.minseok.wheple.coupon

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.coupon.adapter.CouponAdapter

interface CouponContract {

    interface View : BaseView<Presenter> {

        fun connectAdapter()

    }

    interface Presenter : BasePresenter {
        fun getCoupon(price:String, cAdapter:CouponAdapter)
    }
}