package com.minseok.wheple.main


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.minseok.wheple.*
import com.minseok.wheple.HomeFragment
import com.minseok.wheple.chatlist.ChatlistFragment
import com.minseok.wheple.mypage.MypageFragment
import com.minseok.wheple.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var mPresenter : MainContract.Presenter

    val fragment1 = HomeFragment()
    val fragment2 = SearchFragment()
    val fragment3 = ChatlistFragment()
    val fragment4 = MypageFragment()
    val fm = supportFragmentManager
    var active: androidx.fragment.app.Fragment = fragment1



    override fun setPresenter(presenter: MainContract.Presenter) {
        this.mPresenter = presenter
    }



    override fun openFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = fm.beginTransaction()
        transaction.hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener(mPresenter.navListener(fragment1, fragment2, fragment3, fragment4))

            fm.beginTransaction().add(R.id.container, fragment4).hide(fragment4).commit()
            fm.beginTransaction().add(R.id.container, fragment3).hide(fragment3).commit()
            fm.beginTransaction().add(R.id.container, fragment2).hide(fragment2).commit()
            fm.beginTransaction().add(R.id.container, fragment1).commit()



    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {

        if(nav_view.selectedItemId==R.id.navigation_home){

             finishAffinity()

        }else{
            nav_view.selectedItemId = R.id.navigation_home
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }



}

