package com.minseok.wheple.myReservation

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.myReservation.adapter.MyreservationAdapter

interface MyreservationContract {

    interface View : BaseView<Presenter> {
        fun connectAdapter()
        fun showTextNothing()
        fun makeRecycler()
        fun destroyRecycler()
    }

    interface Presenter : BasePresenter {
        fun getlist(myreservationAdapter: MyreservationAdapter)
    }
}
