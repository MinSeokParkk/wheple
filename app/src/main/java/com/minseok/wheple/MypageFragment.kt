package com.minseok.wheple

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*


import kotlinx.android.synthetic.main.fragment_mypage.view.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.app.Activity



class MypageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


            val view: View = inflater.inflate(R.layout.fragment_mypage, container, false)

        val sharedPreferences = context?.getSharedPreferences("currentuser", Context.MODE_PRIVATE)

        if(!sharedPreferences!!.getBoolean("autologin",false)){
            view.constraint_mypage_guest.visibility = View.VISIBLE
            view.constraint_mypage_user.visibility = View.GONE

        } else {
            view.constraint_mypage_guest.visibility = View.GONE
            view.constraint_mypage_user.visibility = View.VISIBLE

        }

            view.mypage_login_button.setOnClickListener {
                activity?.let {


                    val intent = Intent(it, LoginActivity::class.java)
                    it.startActivity(intent)

                }
            }
           val editor: SharedPreferences.Editor = sharedPreferences.edit()

            view.logout_Button.setOnClickListener {




                //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

                editor.clear()
                editor.commit()

                activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
               }

            }


        // Return the fragment view/layout
        return view
    }



    companion object {
        fun newInstance(): MypageFragment = MypageFragment()
    }

}