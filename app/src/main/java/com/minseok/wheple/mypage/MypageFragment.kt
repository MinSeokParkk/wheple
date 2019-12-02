package com.minseok.wheple.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import com.minseok.wheple.login.LoginActivity
import com.minseok.wheple.main.MainActivity
import com.minseok.wheple.myReservation.MyreservationActivity
import com.minseok.wheple.myReview.MyreviewActivity
import com.minseok.wheple.mycoupon.MycouponActivity
import com.minseok.wheple.myinfo.MyinfoActivity
import com.minseok.wheple.mypoint.MypointActivity
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : androidx.fragment.app.Fragment(), MypageContract.View {

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

            view.constraint_myreview.setOnClickListener{
                activity?.let {
                    val intent = Intent(it, MyreviewActivity::class.java)
                    it.startActivity(intent)
                }
            }

            view.constraint_mypage_gotomyinfo.setOnClickListener{
                activity?.let {
                    val intent = Intent(it, MyinfoActivity::class.java)
                    it.startActivity(intent)
                }
            }

            view.const_mypage_coupon.setOnClickListener {
                activity?.let {
                    val intent = Intent(it, MycouponActivity::class.java)
                    it.startActivity(intent)
                }
            }

            view.const_mypage_point.setOnClickListener {
                activity?.let{
                    val intent = Intent(it, MypointActivity::class.java)
                    intent.putExtra("myPoint", text_mypage_point.text.toString())
                    it.startActivity(intent)
                }
            }

        MypagePresenter(this)


        // Return the fragment view/layout
        return view

    }

    override fun onStart() {
        Log.d("MypageFragM","OnStart")
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

    override fun set_myinfo(nickname:String, point:String, photo:String,coupon:String){
        text_mypage_nickname.text = nickname
        text_mypage_point.text = point
        text_mypage_coupon.text = coupon

        Glide.with(this)
            .load(get_base_url()+photo)
            .into(img_mypage_myphoto)
    }

    fun get_base_url() :String{
        return getString(R.string.base_url)
    }



    companion object {
        fun newInstance(): MypageFragment = MypageFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }


}