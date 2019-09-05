package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_reservation.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        backtoSpace_layout.setOnClickListener {
            val nextIntent = Intent(this, SpaceActivity::class.java)
            startActivity(nextIntent)
        }
    }
}