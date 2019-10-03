package com.minseok.wheple.home

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.home.adapter.PlaceAdapter

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun connectAdapter()
        fun makeRecycler()
        fun destroyRecycler()
    }

    interface Presenter : BasePresenter {

        fun getlist(placeAdapter: PlaceAdapter)
        fun clear()
     }
}