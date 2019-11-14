package com.minseok.wheple.search

import android.content.Context
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.search.adapter.SearchRecentAdapter
import com.minseok.wheple.search.adapter.SearchResultAdapter

interface SearchContract {
    interface View : BaseView<Presenter> {

        fun connectAdapter()
        fun deleteOn()
        fun deleteOff()
        fun makeRecycler_recent()
        fun destroyRecycler_recent()
        fun connectAdapter_recent()

    }


    interface Presenter : BasePresenter {
        fun sendSearching(input:String, sAdapter: SearchResultAdapter)
        fun getRecent(rAdapter : SearchRecentAdapter)
        fun refreshRecentR()
    }
}