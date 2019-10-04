package com.minseok.wheple.myResDetail

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView


interface MyResDetailContract {

    interface View : BaseView<Presenter> {
        fun setData(place:String, photo:String, date:String, time:String, name:String, phone:String, price:String,
                    point:String, payment:String, refund_price:String, refund_point:String)
        fun gotoPlace(no:String)
        fun using_used(state:String)
        fun cancel()
    }

    interface Presenter : BasePresenter {
        fun rememeberNo(no: String)
        fun bringData()
        fun settingExtra()

    }
}
