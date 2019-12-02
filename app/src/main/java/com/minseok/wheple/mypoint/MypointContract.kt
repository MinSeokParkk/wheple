package com.minseok.wheple.mypoint

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.mypoint.adapter.MypointAdapter

interface MypointContract {
    interface View : BaseView<Presenter> {

        fun connectAdapter()
    }

    interface Presenter : BasePresenter {
        fun getpoints(mAdapter:MypointAdapter)
    }
}