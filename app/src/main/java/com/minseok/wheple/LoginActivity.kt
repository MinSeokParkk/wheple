package com.minseok.wheple

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup_others.*
import kotlinx.android.synthetic.main.activity_signup_phone.*
import okhttp3.Call
import okhttp3.Callback
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup_text.setOnClickListener {
            val nextIntent = Intent(this, SignupPhoneActivity::class.java)
            startActivity(nextIntent)
        }

        login_Button.setOnClickListener {

            doAsync1(this)


        }


    }

        inner class doAsync1(context: Context) : AsyncTask<Void, Void, String>() {
            private val context: Context

            init {
                execute()
                this.context = context.applicationContext
            }

            override fun doInBackground(vararg params: Void?): String? {


                var res: String = ""

                var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(
                    login_email_editText.text.toString(),
                    "UTF-8"
                )
                reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
                    login_password_editText.text.toString(),
                    "UTF-8"
                )
                val mURL = URL("http://172.30.1.53:9999/login.php")

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

                        if (response.toString().equals("0")) {
                            println("intent")
                        }

                        res = response.toString()

                    }
                }
                return res
            }

            override fun onPostExecute(res: String?) {
                if (res.equals("0")) {
                    println("oooooo")


                    val sharedPreferences = getSharedPreferences("currentuser", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("email", login_email_editText.text.toString())
                    editor.putBoolean("autologin", true)
                    editor.apply()


                    val nextIntent = Intent(context, MainActivity::class.java)
                    startActivity(nextIntent)

                } else if (res.equals("1")) {
                    Toast.makeText(context, "가입하지 않은 아이디입니다.", Toast.LENGTH_SHORT).show()
                    login_email_editText.requestFocus()
                } else if (res.equals("2")) {

                    Toast.makeText(context, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    login_password_editText.text.clear()
                }
            }


        }

    }


