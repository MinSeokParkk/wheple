package com.minseok.wheple.chatlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.ChatroomActivity
import com.minseok.wheple.R
import com.minseok.wheple.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_chatlist.*
import kotlinx.android.synthetic.main.fragment_chatlist.view.*

class ChatlistFragment : androidx.fragment.app.Fragment(), ChatlistContract.View {

   private lateinit var mPresenter : ChatlistContract.Presenter

    override fun setPresenter(presenter: ChatlistContract.Presenter) {
        this.mPresenter = presenter
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_chatlist, container, false)

        view.constraintLayout10.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ChatroomActivity::class.java)
                it.startActivity(intent)
            }
        }

        view.chatlist_login_button.setOnClickListener {
            activity?.let {

                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)

            }
        }


        ChatlistPresenter(this)

        // Return the fragment view/layout
        return view

    }

    override fun onResume() {
        super.onResume()
        mPresenter.check_preference()
    }

    override fun login_mode(){
        constraint_chatlist_guest.visibility = View.GONE
        constraint_chatlist_user.visibility = View.VISIBLE
    }

    override fun guest_mode() {
        constraint_chatlist_guest.visibility = View.VISIBLE
        constraint_chatlist_user.visibility = View.GONE
    }

    companion object {
        fun newInstance(): ChatlistFragment = ChatlistFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }
}