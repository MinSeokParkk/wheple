package com.minseok.wheple.myinfoPhone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_myinfo_modifier_phone.*

class MyinfoPhoneActivity: AppCompatActivity(), MyinfoPhoneContract.View {
    private lateinit var mPresenter:  MyinfoPhoneContract.Presenter

    override fun setPresenter(presenter:  MyinfoPhoneContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myinfo_modifier_phone)
        mPresenter = MyinfoPhonePresenter(this)

        edit_myinfoM_phone.setText(intent.getStringExtra("phone"))

        img_myinfoM_phone_back.setOnClickListener {
            onBackPressed()
        }

        text_myinfoM_phone_delete.setOnClickListener {
            edit_myinfoM_phone.text.clear()
            deleteOff()
        }

        edit_myinfoM_phone.afterTextChanged {
            text_myinfoM_phone_alarm.visibility = View.GONE
            edit_myinfoM_phone.setBackgroundResource(R.drawable.edittext_background)
            buttonOff()
            mPresenter.changePhone(edit_myinfoM_phone.text.toString(), intent.getStringExtra("phone"))
        }

        button_myinfoM_phone.setOnClickListener {
            mPresenter.savePhone(edit_myinfoM_phone.text.toString())
        }
    }


    override fun deleteOn() {
        text_myinfoM_phone_delete.visibility = View.VISIBLE
    }

    override fun deleteOff(){
        text_myinfoM_phone_delete.visibility = View.GONE
    }

    override fun alarmOn(string: String){
        text_myinfoM_phone_alarm.text = string
        text_myinfoM_phone_alarm.visibility = View.VISIBLE
        edit_myinfoM_phone.setBackgroundResource(R.drawable.edittext_background_red)
    }

    override fun buttonOn(){
        button_myinfoM_phone.visibility=View.VISIBLE
        button_myinfoM_phone.setBackgroundResource(R.drawable.button_on)
        button_myinfoM_phone.isEnabled =true
    }

    override fun buttonOff(){
        button_myinfoM_phone.visibility=View.GONE
    }

    override fun wrongInput(){
        edit_myinfoM_phone.requestFocus()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun back() {
        onBackPressed()
    }



}