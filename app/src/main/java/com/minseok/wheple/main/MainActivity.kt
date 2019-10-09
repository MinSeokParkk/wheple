package com.minseok.wheple.main


import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import com.minseok.wheple.*
import com.minseok.wheple.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var mPresenter : MainContract.Presenter

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.mPresenter = presenter
    }



    override fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener(mPresenter.navListener())



        if (savedInstanceState == null) { //기본은 homefragment로 한다.

            supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        }


    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {

        if(nav_view.selectedItemId==R.id.navigation_home){

             finishAffinity()

        }else{
            nav_view.selectedItemId = R.id.navigation_home
        }

    }

}

