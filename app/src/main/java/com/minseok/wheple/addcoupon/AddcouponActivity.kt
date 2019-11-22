package com.minseok.wheple.addcoupon

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_addcoupon.*

class AddcouponActivity : AppCompatActivity(), AddcouponContract.View{

    private lateinit var mPresenter :AddcouponContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcoupon)
        AddcouponPresenter(this)

        //알파벳 소문자 입력시 무조건 대문자로 바꿔줌
        edit_addc.setFilters(arrayOf<InputFilter>(InputFilter.AllCaps()))

        img_addc_back.setOnClickListener{
            onBackPressed()
        }

        edit_addc.afterTextChanged {
            text_addc_alarm.visibility = View.GONE
            edit_addc.setBackgroundResource(R.drawable.edittext_background)
            buttonOff()
            mPresenter.changeCoupon(edit_addc.text.toString())
        }

        button_addc.setOnClickListener {
            mPresenter.saveCoupon(edit_addc.text.toString())
        }
    }

    override fun setPresenter(presenter:AddcouponContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun alarmOn(string:String){
        text_addc_alarm.text = string
        text_addc_alarm.visibility = View.VISIBLE
        edit_addc.setBackgroundResource(R.drawable.edittext_background_red)
    }

    override fun setCoupon(coupon:String){
        edit_addc.setText(coupon)
        edit_addc.setSelection(edit_addc.text.length) //커서 뒤로 보내주는 역할
    }

    override fun buttonOn(){
        button_addc.visibility = View.VISIBLE
    }

    override fun buttonOff(){
        button_addc.visibility = View.GONE
    }

    override fun back() {
        onBackPressed()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun wrongInput(){
        edit_addc.requestFocus()
    }

}