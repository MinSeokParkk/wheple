package com.minseok.wheple.test

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_test2.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Test2Activity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)



            val url = "http://172.30.1.53:9999/test2.php"
            GetWeatherAsyncTask().execute(url)

    }

    inner class GetWeatherAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            // Before doInBackground
        }

        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val url = URL(urls[0])

                urlConnection = url.openConnection() as HttpURLConnection


                var inString = streamToString(urlConnection.inputStream)

                publishProgress(inString)
            } catch (ex: Exception) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json = JSONObject(values[0])

                val name = json.getString("name")


                val age = json.getString("age")

                name_textview.text = name.toString()
                age_textview.text = age.toString()


            } catch (ex: Exception) {

            }
        }

        override fun onPostExecute(result: String?) {
            // Done
        }
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return result
    }
}



