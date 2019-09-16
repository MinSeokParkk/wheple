package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.minseok.wheple.main.MainActivity
import kotlinx.android.synthetic.main.activity_reservation_success.*

class ReservationSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_success)

        home_Button.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
    }
}