package com.minseok.wheple.main

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.minseok.wheple.HomeFragment
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.chatlist.ChatlistFragment
import com.minseok.wheple.mypage.MypageFragment
import com.minseok.wheple.search.SearchFragment

interface MainContract {

    interface View : BaseView<Presenter> {
        fun openFragment(fragment: androidx.fragment.app.Fragment)
    }

    interface Presenter : BasePresenter {
        fun navListener(fragment1: HomeFragment, fragment2: SearchFragment,
                        fragment3: ChatlistFragment, fragment4 : MypageFragment
        ) : BottomNavigationView.OnNavigationItemSelectedListener
    }
}