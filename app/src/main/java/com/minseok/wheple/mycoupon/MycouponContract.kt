package com.minseok.wheple.mycoupon

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.mycoupon.adapter.MycouponAdapter

interface MycouponContract {
    interface View : BaseView<Presenter> {
        fun connectAdapter()
        fun setNum(num:String)
        fun nocoupon()
    }

    interface Presenter : BasePresenter {
        fun getCoupon(cAdapter: MycouponAdapter)
    }
}