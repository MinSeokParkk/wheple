package com.minseok.wheple.search

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface SearchContract {
    interface View : BaseView<Presenter> {


    }


    interface Presenter : BasePresenter {
        fun sendSearching(input:String)
    }
}