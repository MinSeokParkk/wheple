package com.minseok.wheple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup_others.*
import kotlinx.android.synthetic.main.activity_test.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class SignupOthersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_others)

        signup_finish_Button.setOnClickListener {

            doAsync {
                sendUserdata(signup_email_editText.text.toString(), signup_password_editText.text.toString(), signup_nickname_editText.text.toString(), intent.getStringExtra("phone"))
            }

            val sharedPreferences = getSharedPreferences("currentuser", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", signup_email_editText.text.toString())
            editor.putBoolean("autologin", true)
            editor.apply()


            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

    }


    fun sendUserdata(email:String, password:String, nickname:String, phone:String) {

        var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
        reqParam += "&" + URLEncoder.encode("nickname", "UTF-8") + "=" + URLEncoder.encode(nickname, "UTF-8")
        reqParam += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
        val mURL = URL("http://172.30.1.53:9999/signup.php")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "POST"

            val wr = OutputStreamWriter(getOutputStream())
            wr.write(reqParam)
            wr.flush()

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")

                fun Context.toast(message: CharSequence) =
                    Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
            }
        }
    }

}