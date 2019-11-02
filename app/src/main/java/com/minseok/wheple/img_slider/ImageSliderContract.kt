package com.minseok.wheple.img_slider

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface ImageSliderContract {

    interface View : BaseView<Presenter> {
        fun setAdapter(imageList: ArrayList<String>)
    }

    interface Presenter : BasePresenter {
        fun setting(images:Array<String>)
    }
}