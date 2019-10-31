package com.minseok.wheple.review

import android.widget.TextView
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.review.adapter.ReviewAdapter

interface ReviewContract {

    interface View : BaseView<Presenter> {
        fun sortTextChange(textview: TextView)
        fun setSortText(text:String)
        fun get_base_url() :String
        fun connectAdapter()
        fun showNothing(nophoto:Boolean)

    }


    interface Presenter : BasePresenter {
        fun basicSort(fresh:TextView, good:TextView, bad:TextView)
        fun sortSelected(sort:String)
        fun photoSwitchChange(photo:Boolean)
        fun getlist(rAdapter: ReviewAdapter)
        fun paging(rAdapter: ReviewAdapter)
        fun page0()
    }
}