package com.minseok.wheple.main


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.minseok.wheple.*
import com.minseok.wheple.HomeFragment
import com.minseok.wheple.near.NearFragment
import com.minseok.wheple.mypage.MypageFragment
import com.minseok.wheple.dibs.DibsFragment
import com.minseok.wheple.myReservation.MyreservationActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var mPresenter : MainContract.Presenter

    val fragment1 = HomeFragment()
    val fragment2 = DibsFragment()
    val fragment3 = NearFragment()
    val fragment4 = MypageFragment()
    val fm = supportFragmentManager
    var active: androidx.fragment.app.Fragment = fragment1
    var save = false

    companion object{
        var afterRes : Boolean = false
    }


    override fun setPresenter(presenter: MainContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun openFragment(fragment: androidx.fragment.app.Fragment) {
        Log.d("navtest",  "openFragment")
        val transaction = fm.beginTransaction()
        transaction.hide(active).show(fragment).commit()
        active = fragment
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = MainPresenter(this)
        super.onCreate(null)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigation.setOnNavigationItemSelectedListener(mPresenter.navListener(fragment1, fragment2, fragment3, fragment4))



        fm.beginTransaction().add(R.id.container, fragment4).hide(fragment4).commit()
        fm.beginTransaction().add(R.id.container, fragment3).hide(fragment3).commit()
        fm.beginTransaction().add(R.id.container, fragment2).hide(fragment2).commit()
        fm.beginTransaction().add(R.id.container, fragment1).commit()

        if (savedInstanceState != null) {
          save = true
        }






    }

    override fun onResume() {
        super.onResume()
        if(save){ //saveinstance 가 있으면 무조건 홈으로 바꾼다.
            nav_view.selectedItemId = R.id.navigation_home
            save = false
        }

        ////////////////실험중
        if(afterRes){

            nav_view.selectedItemId = R.id.navigation_mypage

            val nextIntent = Intent(this, MyreservationActivity::class.java)
            startActivity(nextIntent)

            afterRes = false
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {
        Log.d("navtest",  "onBackpressed")
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

