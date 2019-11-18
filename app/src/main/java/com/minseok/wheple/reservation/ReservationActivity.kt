package com.minseok.wheple.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.ReservationSuccessActivity
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.coupon.CouponActivity
import com.minseok.wheple.select_date_time.SelectDateTimeActivity
import kotlinx.android.synthetic.main.activity_reservation.*

class ReservationActivity : AppCompatActivity(), ReservationContract.View{

    companion object{
        var couponchange = false
        var couponcount = 0
        var couponNo = ""
    }

    private lateinit var mPresenter : ReservationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        ReservationPresenter(this)
//        mPresenter.temp_reserve(intent.getStringExtra("space"), intent.getStringExtra("date"), intent.getStringExtra("timeNo"))
        mPresenter.showDetail(intent.getStringExtra("space"), intent.getStringExtra("date"), intent.getStringExtra("timeNo"), intent.getStringExtra("timeText"))

        check_res_all.setOnCheckedChangeListener { buttonView, isChecked ->
            mPresenter.checkchange(check_res_all.isChecked)
        }

        edit_res_point.afterTextChanged{ // text_res_price_mid.text.toString() 를 중간금액 - 쿠폰금액으로 바꿔야 할듯!
            mPresenter.inputPointCheck(text_res_price_mid.text.toString(), edit_res_point.text.toString(), edit_res_point.hint.toString())
        }

        text_res_allpoint.setOnClickListener{
            println(edit_res_point.hint)

            mPresenter.useAllPoint(edit_res_point.hint.toString())
        }

        edit_res_name.afterTextChanged {
            fillingCheck()
        }

        edit_res_phone.afterTextChanged {
            fillingCheck()
        }

        check_res_use.setOnCheckedChangeListener { buttonView, isChecked ->
            fillingCheck()
        }

        check_res_cancel.setOnCheckedChangeListener { buttonView, isChecked ->
            fillingCheck()
        }

        check_res_personal.setOnCheckedChangeListener { buttonView, isChecked ->
            fillingCheck()
        }


        button_gotopay.setOnClickListener {
            mPresenter.reserve_check(edit_res_name.text.toString(), edit_res_phone.text.toString(),
            check_res_use.isChecked, check_res_cancel.isChecked, check_res_personal.isChecked, text_res_date.text.toString(),
            intent.getStringExtra("timeNo"), intent.getStringExtra("space"), text_res_time.text.toString(),
            text_res_price_top.text.toString(), text_res_finalprice.text.toString(), edit_res_point.text.toString())
        }

        img_res_back.setOnClickListener {
            onBackPressed()
        }

        button_res_coupon.setOnClickListener {
            val nextIntent = Intent(this, CouponActivity::class.java)
            nextIntent.putExtra("price",  text_res_price_mid.text.toString())
            startActivity(nextIntent)
        }


    }

    override fun onStart() {
        super.onStart()
        //여기에 변화된 내용을 쓰자
    }


    override fun setPresenter(presenter: ReservationContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun setRes(name:String, date: String, timeText: String, price:String, hour:String, totalprice:String,
                        phone:String, point:String, username:String, coupon:Int) {
        text_res_name.text = name
        text_res_date.text = date
        text_res_time.text = timeText
        text_res_timehour.text = price+"원 X "+hour+"시간"
        text_res_price_top.text = totalprice+"원"
        text_res_price_mid.text = totalprice+"원"
        edit_res_phone.setText(phone)
        text_res_possiblepoint.text = point+"P 사용가능"
        edit_res_point.hint = point+"P 보유"
        text_res_finalprice.text = totalprice+"원"
        button_gotopay.text = totalprice+"원 결제하기"
        edit_res_name.setText(username)
        text_res_coupon_use.text = text_res_coupon_use.text.toString() + coupon.toString() +"장"

    }

    override fun otherscheck(check: Boolean) {
        check_res_cancel.isChecked = check
        check_res_personal.isChecked = check
        check_res_use.isChecked = check
    }

    override fun writePoint(point: String) {
        println("롸이트 포인트")
        edit_res_point.setText(point)
        edit_res_point.setSelection(edit_res_point.text.length)
    }

    override fun setPrice(price: String) {
        text_res_finalprice.text = price
        button_gotopay.text = price+" 결제하기"
    }

    override fun gotoReservationSuccess(){
        val nextIntent = Intent(this, ReservationSuccessActivity::class.java)
        startActivity(nextIntent)
        finish()
    }

    internal fun fillingCheck(){
        mPresenter.inputCheck(edit_res_name.text.toString(), edit_res_phone.text.toString(),
                              check_res_use.isChecked, check_res_cancel.isChecked, check_res_personal.isChecked)
    }

    override fun paybutton_on() {
        button_gotopay.setBackgroundResource(R.drawable.button_on)
    }

    override fun paybutton_off() {
        button_gotopay.setBackgroundResource(R.drawable.button_off)
    }

    override fun wrongInput(int: Int) {
        if(int==1){ //이름 입력 오류
            edit_res_name.requestFocus()
            scroll_res.smoothScrollTo(0,edit_res_name.scrollY)
        }else if(int ==2){ //휴대폰 번호 입력 오류
            edit_res_phone.requestFocus()
            scroll_res.smoothScrollTo(0,edit_res_phone.scrollY)
        }else if(int==3){ //약관에 동의 안함
            check_res_all.requestFocus()
        }
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun lateReserve() {  //다른 사람이 이미 예약 완료했을때

        val builder = AlertDialog.Builder(this)
        builder.setTitle("예약이 불가능합니다.")
        builder.setMessage("\n죄송합니다.\n선택하신 시간이 마감되었습니다.\n\n다른 시간을 선택해주세요.")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

          SelectDateTimeActivity.MyClass.activity?.finish()
         onBackPressed()

        }

        builder.show()
    }

    override fun nocoupon(){
        text_res_coupon.visibility = View.GONE
        text_res_coupon_use.visibility = View.GONE
        button_res_coupon.visibility = View.GONE
    }



}