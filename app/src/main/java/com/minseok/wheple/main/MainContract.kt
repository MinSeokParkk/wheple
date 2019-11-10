package com.minseok.wheple.main

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.minseok.wheple.HomeFragment
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.near.NearFragment
import com.minseok.wheple.mypage.MypageFragment
import com.minseok.wheple.dibs.DibsFragment

interface MainContract {

    interface View : BaseView<Presenter> {
        fun openFragment(fragment: androidx.fragment.app.Fragment)
    }

    interface Presenter : BasePresenter {
        fun navListener(fragment1: HomeFragment, fragment2: DibsFragment,
                        fragment3: NearFragment, fragment4 : MypageFragment
        ) : BottomNavigationView.OnNavigationItemSelectedListener
    }
}