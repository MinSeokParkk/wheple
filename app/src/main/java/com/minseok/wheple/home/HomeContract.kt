package com.minseok.wheple.home

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.retrofit.Result

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun makeadapter(places: Result.Connectresult)

    }

    interface Presenter : BasePresenter {
        fun getlist()
    }
}