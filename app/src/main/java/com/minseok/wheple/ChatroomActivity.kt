package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.minseok.wheple.place.PlaceActivity
import kotlinx.android.synthetic.main.activity_chat.*

class ChatroomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        backtoSpace_layout.setOnClickListener {
            val nextIntent = Intent(this, PlaceActivity::class.java)
            startActivity(nextIntent)
        }
    }
}