package com.minseok.wheple.home

import android.content.Context
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter{
            var homeData:HomeData

            var adapterModel: HomeAdapterContract.Model
            var adapterView: HomeAdapterContract.View

            fun loadItems(context: Context, isClear:Boolean)

    }
}