package com.minseok.wheple.test



import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.minseok.wheple.R

import kotlinx.android.synthetic.main.activity_test.*

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        testSave_button.setOnClickListener {


            doAsync {
                sendPostRequest(testName_editText.text.toString(), testAge_editText.text.toString())
            }

            val nextIntent = Intent(this, Test2Activity::class.java)
            startActivity(nextIntent)

        }


    }

    fun sendPostRequest(name:String, age:String) {

        var reqParam = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
        reqParam += "&" + URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")
        val mURL = URL("http://172.30.1.53:9999/test.php")

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
            }
        }
    }



}

