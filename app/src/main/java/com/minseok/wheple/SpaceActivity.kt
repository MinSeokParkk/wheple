package com.minseok.wheple


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_space.*


class SpaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space)

            ReviewMore1_layout.setOnClickListener {
                val nextIntent = Intent(this, ReviewActivity::class.java)
                startActivity(nextIntent)
            }

            ReviewMore2_layout.setOnClickListener {
              val nextIntent = Intent(this, ReviewActivity::class.java)
              startActivity(nextIntent)
            }

            select_date_time_Button.setOnClickListener {
               val nextIntent = Intent(this, SelectDateTimeActivity::class.java)
                startActivity(nextIntent)
            }


    }
}