package com.minseok.wheple.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.minseok.wheple.*
import com.minseok.wheple.chatlist.ChatlistFragment
import com.minseok.wheple.home.HomeFragment
import com.minseok.wheple.mypage.MypageFragment
import com.minseok.wheple.search.SearchFragment

class MainPresenter (private val view : MainContract.View): MainContract.Presenter{

    override fun start() {
    }

    init {
        this.view.setPresenter(this)
    }

    override fun navListener(): BottomNavigationView.OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            menuItem ->
        val selectedFragment: Fragment
        when(menuItem.itemId){
            R.id.navigation_home -> {
                selectedFragment = HomeFragment()
                view.openFragment(selectedFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                selectedFragment = SearchFragment()
                view.openFragment(selectedFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chat -> {
                selectedFragment = ChatlistFragment()
                view.openFragment(selectedFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mypage -> {
                selectedFragment = MypageFragment()
                view.openFragment(selectedFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}