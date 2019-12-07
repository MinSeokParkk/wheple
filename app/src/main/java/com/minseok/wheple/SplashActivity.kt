package com.minseok.wheple

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.minseok.wheple.main.MainActivity

class SplashActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)
        finish()
    }

}