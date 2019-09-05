package com.minseok.wheple


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_signup_phone.*

class SignupPhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_phone)

        signup_next_Button.setOnClickListener {
            val nextIntent = Intent(this, SignupOthersActivity::class.java)
            nextIntent.putExtra("phone", signup_phone_editText.text.toString())
            startActivity(nextIntent)


        }
    }
}