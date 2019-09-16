package com.minseok.wheple.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {
        fun openFragment(fragment: Fragment)
    }

    interface Presenter : BasePresenter {
        fun navListener() : BottomNavigationView.OnNavigationItemSelectedListener
    }
}