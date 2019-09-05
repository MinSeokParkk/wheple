package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reservation.*

class ReservationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

            payment_Button.setOnClickListener {
            val nextIntent = Intent(this, ReservationSuccessActivity::class.java)
            startActivity(nextIntent)
        }
    }
}