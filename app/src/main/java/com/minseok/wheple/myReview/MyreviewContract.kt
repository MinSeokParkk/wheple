package com.minseok.wheple.myReview

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.myReview.adapter.MyreviewAdapter

interface MyreviewContract {
    interface View : BaseView<Presenter> {
        fun connectAdapter()
        fun showTextNothing()
        fun get_base_url() :String

    }

    interface Presenter : BasePresenter {
        fun getlist(myreviewAdapter: MyreviewAdapter)
        fun delete(no:String)
    }
}