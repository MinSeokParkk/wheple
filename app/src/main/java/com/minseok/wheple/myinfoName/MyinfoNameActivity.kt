package com.minseok.wheple.myinfoName

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_myinfo_modifier_nickname.*
import kotlinx.android.synthetic.main.dialog_filter_sports.*

class MyinfoNameActivity: AppCompatActivity(), MyinfoNameContract.View {
    private lateinit var mPresenter:  MyinfoNameContract.Presenter

    override fun setPresenter(presenter:  MyinfoNameContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myinfo_modifier_nickname)
        mPresenter = MyinfoNamePresenter(this)

        text_myinfoM_subject.text = "이름 수정"
        text_myinfoM_nameExplain.visibility = View.VISIBLE
        button_myinfoM_nickname.text = "이름 저장"
        edit_myinfoM_nickname.hint = "반드시 실명을 입력해주세요."

        if(intent.getStringExtra("name")==""){
           deleteOff()
        }
        edit_myinfoM_nickname.setText(intent.getStringExtra("name"))

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
            mPresenter.changeName(edit_myinfoM_nickname.text.toString(), intent.getStringExtra("name"))
        }

        button_myinfoM_nickname.setOnClickListener {
            mPresenter.saveName(edit_myinfoM_nickname.text.toString())
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

    override fun buttonOn(){
        button_myinfoM_nickname.visibility=View.VISIBLE
        button_myinfoM_nickname.setBackgroundResource(R.drawable.button_on)
        button_myinfoM_nickname.isEnabled =true
    }

    override fun buttonOff(){
        button_myinfoM_nickname.visibility=View.GONE
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun back() {
        onBackPressed()
    }

    override fun setName(name:String){
        edit_myinfoM_nickname.setText(name)
        edit_myinfoM_nickname.setSelection(edit_myinfoM_nickname.text.length)
    }

}