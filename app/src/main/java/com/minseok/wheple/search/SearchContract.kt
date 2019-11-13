package com.minseok.wheple.search

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.search.adapter.SearchResultAdapter

interface SearchContract {
    interface View : BaseView<Presenter> {
        fun connectAdapter()
        fun deleteOn()
        fun deleteOff()

    }


    interface Presenter : BasePresenter {
        fun sendSearching(input:String, sAdapter: SearchResultAdapter)
    }
}