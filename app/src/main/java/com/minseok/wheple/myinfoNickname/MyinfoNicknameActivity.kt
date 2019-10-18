package com.minseok.wheple.myinfoNickname

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_myinfo_modifier_nickname.*

class MyinfoNicknameActivity  : AppCompatActivity(),MyinfoNicknameContract.View {
    private lateinit var mPresenter: MyinfoNicknameContract.Presenter

    override fun setPresenter(presenter: MyinfoNicknameContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myinfo_modifier_nickname)
        mPresenter = MyinfoNicknamePresenter(this)

        edit_myinfoM_nickname.setText(intent.getStringExtra("nickname"))

        img_myinfoM_nickname_back.setOnClickListener {
            onBackPressed()
        }

        text_myinfoM_nickname_delete.setOnClickListener {
            edit_myinfoM_nickname.text.clear()
            deleteOff()
        }

        edit_myinfoM_nickname.afterTextChanged {
            text_myinfoM_nickname_alarm.visibility = View.GONE
            edit_myinfoM_nickname.setBackgroundResource(R.drawable.edittext_background)
           buttonOff()
            mPresenter.changeNickname(edit_myinfoM_nickname.text.toString(), intent.getStringExtra("nickname"))
        }

        button_myinfoM_nickname.setOnClickListener {
            mPresenter.saveNickname(edit_myinfoM_nickname.text.toString())
        }

    }

    override fun deleteOn() {
        text_myinfoM_nickname_delete.visibility = View.VISIBLE
    }

    override fun deleteOff(){
        text_myinfoM_nickname_delete.visibility = View.GONE
    }

    override fun alarmOn(string:String){
        text_myinfoM_nickname_alarm.text = string
        text_myinfoM_nickname_alarm.visibility = View.VISIBLE
        edit_myinfoM_nickname.setBackgroundResource(R.drawable.edittext_background_red)
    }
    override fun setNickname(nickname:String){
        edit_myinfoM_nickname.setText(nickname)
        edit_myinfoM_nickname.setSelection(edit_myinfoM_nickname.text.length)
    }

    override fun buttonOn(){
        button_myinfoM_nickname.visibility=View.VISIBLE
        button_myinfoM_nickname.setBackgroundResource(R.drawable.button_on)
        button_myinfoM_nickname.isEnabled =true
    }

    override fun buttonOff(){
        button_myinfoM_nickname.visibility=View.GONE
    }

    override fun wrongInput(){
        edit_myinfoM_nickname.requestFocus()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun back() {
        onBackPressed()
    }

}