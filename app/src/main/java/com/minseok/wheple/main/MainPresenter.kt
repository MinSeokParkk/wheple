package com.minseok.wheple.main

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.minseok.wheple.*
import com.minseok.wheple.near.NearFragment
import com.minseok.wheple.HomeFragment
import com.minseok.wheple.mypage.MypageFragment
import com.minseok.wheple.search.SearchFragment

class MainPresenter (private val view : MainContract.View): MainContract.Presenter{

    override fun start() {
    }

    init {
        this.view.setPresenter(this)
    }

    override fun navListener(fragment1: HomeFragment, fragment2: SearchFragment,
                             fragment3: NearFragment, fragment4 : MypageFragment
    ): BottomNavigationView.OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            menuItem ->
        val selectedFragment: androidx.fragment.app.Fragment
          when(menuItem.itemId){
            R.id.navigation_home -> {

                    view.openFragment(fragment1)

                    return@OnNavigationItemSelectedListener true


            }
            R.id.navigation_search -> {
                view.openFragment(fragment2)

                return@OnNavigationItemSelectedListener true


            }
            R.id.navigation_chat -> {
                view.openFragment(fragment3)

                return@OnNavigationItemSelectedListener true

            }
            R.id.navigation_mypage -> {
                view.openFragment(fragment4)

                    return@OnNavigationItemSelectedListener true

            }
        }
        false
    }
}