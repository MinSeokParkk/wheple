package com.minseok.wheple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_chatlist.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*

class ChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_chatlist, container, false)

        val sharedPreferences = context?.getSharedPreferences("currentuser", Context.MODE_PRIVATE)


        if(!sharedPreferences!!.getBoolean("autologin",false)){
            view.constraint_chatlist_guest.visibility = View.VISIBLE
            view.constraint_chatlist_user.visibility = View.GONE

        } else {
            view.constraint_chatlist_guest.visibility = View.GONE
            view.constraint_chatlist_user.visibility = View.VISIBLE

        }

        view.constraintLayout10.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ChatActivity::class.java)
                it.startActivity(intent)
            }
        }

        view.chatlist_login_button.setOnClickListener {
            activity?.let {


                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)

            }
        }


        // Return the fragment view/layout
        return view
    }

    companion object {
        fun newInstance(): ChatFragment = ChatFragment()
    }
}