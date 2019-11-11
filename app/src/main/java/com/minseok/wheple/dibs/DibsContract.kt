package com.minseok.wheple.dibs

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.dibs.adapter.DibsAdpater

interface DibsContract {
    interface View : BaseView<Presenter> {
        fun makeRecycler()
        fun connectAdapter()
        fun destroyRecycler()
        fun login_mode()
        fun logout_mode()
        fun setNumber(num:Int)
    }

    interface Presenter : BasePresenter {
        fun getlist(dibsAdpater: DibsAdpater)
        fun check_preference()
     }


}