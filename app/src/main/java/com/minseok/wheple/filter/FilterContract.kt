package com.minseok.wheple.filter

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface FilterContract{
    interface View : BaseView<Presenter> {
        fun setting(c1:Boolean, c2:Boolean, c3:Boolean, c4:Boolean, c5:Boolean, c6:Boolean,
                    c7:Boolean, c8:Boolean, c9:Boolean, c10:Boolean, c11:Boolean, c12:Boolean,
                    loc1: String, loc2: String)
        fun dismiss()
    }

    interface Presenter : BasePresenter {
       fun get_filterdata()
        fun adjust(c1:Boolean, c2:Boolean, c3:Boolean, c4:Boolean, c5:Boolean, c6:Boolean,
                   c7:Boolean, c8:Boolean, c9:Boolean, c10:Boolean, c11:Boolean, c12:Boolean,
                   loc1: String, loc2: String)
        fun reset()
    }
}