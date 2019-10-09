package com.minseok.wheple.mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import com.minseok.wheple.login.LoginActivity
import com.minseok.wheple.main.MainActivity
import com.minseok.wheple.myReservation.MyreservationActivity
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment(), MypageContract.View {

    private lateinit var mPresenter : MypageContract.Presenter

    override fun setPresenter(presenter: MypageContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val view: View = inflater.inflate(R.layout.fragment_mypage, container, false)

            view.mypage_login_button.setOnClickListener {
                activity?.let {


                    val intent = Intent(it, LoginActivity::class.java)
                    it.startActivity(intent)

                }
            }



            view.constraint_logout.setOnClickListener {

                //나중에 옮길 계획 (로그아웃 : 쉐어드 지우는 코드)
                    mPresenter.logout()
                ////////////////

                activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
               }

            }

            view.constraint_reservation.setOnClickListener {
                activity?.let {
                    val intent = Intent(it, MyreservationActivity::class.java)
                    it.startActivity(intent)
                }
            }

        MypagePresenter(this)



        // Return the fragment view/layout
        return view

    }

    override fun onStart() {
        mPresenter.check_preference()
        super.onStart()
    }

    override fun login_mode() {
        constraint_mypage_guest.visibility = View.GONE
        constraint_mypage_user.visibility = View.VISIBLE
    }

    override fun guest_mode() {
        constraint_mypage_guest.visibility = View.VISIBLE
        constraint_mypage_user.visibility = View.GONE
    }

    override fun set_myinfo(nickname:String, point:String){
        text_mypage_nickname.text = nickname
        text_mypage_point.text = point
    }



    companion object {
        fun newInstance(): MypageFragment = MypageFragment()
    }

}