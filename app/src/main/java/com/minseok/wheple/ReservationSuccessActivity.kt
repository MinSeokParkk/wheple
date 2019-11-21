package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        button_goMyres.setOnClickListener {
            MainActivity.afterRes = true
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }


    }

    override fun onBackPressed() {

    }
}