package com.minseok.wheple.myinfoPw

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_myinfo_modifier_password.*

class MyinfoPwActivity : AppCompatActivity(),  MyinfoPwContract.View {
    private lateinit var mPresenter:   MyinfoPwContract.Presenter

    override fun setPresenter(presenter:   MyinfoPwContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myinfo_modifier_password)
        mPresenter = MyinfoPwPresenter(this)

        img_myinfoM_pw_back.setOnClickListener {
            onBackPressed()
        }

        text_myinfoM_pw_oldpw_showing.setOnClickListener {
            passwordshowing(text_myinfoM_pw_oldpw_showing, edit_myinfoM_pw_oldpw)

        }

        text_myinfoM_pw_newpw_showing.setOnClickListener {
            passwordshowing(text_myinfoM_pw_newpw_showing, edit_myinfoM_pw_newpw)
        }

        text_myinfoM_pw_newcheck_showing.setOnClickListener {
            passwordshowing(text_myinfoM_pw_newcheck_showing, edit_myinfoM_pw_newcheck)
        }

        edit_myinfoM_pw_oldpw.afterTextChanged {
            text_myinfoM_pw_oldpw_alarm.visibility = View.GONE
            edit_myinfoM_pw_oldpw.setBackgroundResource(R.drawable.edittext_background)
            button_off(button_myinfoM_pw_oldpw)
            mPresenter.changeOldPw(edit_myinfoM_pw_oldpw, text_myinfoM_pw_oldpw_showing,
                text_myinfoM_pw_oldpw_alarm, button_myinfoM_pw_oldpw,
                edit_myinfoM_pw_oldpw.text.toString())
        }

        button_myinfoM_pw_oldpw.setOnClickListener {
            mPresenter.checkOldPw(edit_myinfoM_pw_oldpw, text_myinfoM_pw_oldpw_alarm,
                 button_myinfoM_pw_oldpw, edit_myinfoM_pw_oldpw.text.toString())
        }

        edit_myinfoM_pw_newpw.afterTextChanged {
            text_myinfoM_pw_newpw_alarm.visibility = View.INVISIBLE
            edit_myinfoM_pw_newpw.setBackgroundResource(R.drawable.edittext_background)


            button_off(button_myinfoM_pw_newpw)
            mPresenter.checkNewPw(edit_myinfoM_pw_newpw, text_myinfoM_pw_newpw_showing,
                                  text_myinfoM_pw_newpw_alarm, edit_myinfoM_pw_newpw.text.toString(),
                                  edit_myinfoM_pw_oldpw.text.toString(), edit_myinfoM_pw_newcheck.text.toString())

        }

        edit_myinfoM_pw_newcheck.afterTextChanged {
            text_myinfoM_pw_newcheck_alarm.visibility = View.INVISIBLE
            edit_myinfoM_pw_newcheck.setBackgroundResource(R.drawable.edittext_background)

            button_off(button_myinfoM_pw_newpw)
            mPresenter.checkNewCheck(edit_myinfoM_pw_newcheck, text_myinfoM_pw_newcheck_showing,
                                     text_myinfoM_pw_newcheck_alarm, edit_myinfoM_pw_newcheck.text.toString(),
                                     edit_myinfoM_pw_newpw.text.toString())

        }

        button_myinfoM_pw_newpw.setOnClickListener {
            mPresenter.saveNewPw(edit_myinfoM_pw_newpw.text.toString())
        }



    }



    fun passwordshowing(textView: TextView, editText: EditText){
        if(textView.text.toString()=="\uf06e"){
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            editText.setSelection(editText.text.length)
            textView.text="\uf070"
        }else{
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setSelection(editText.text.length)
            textView.text ="\uf06e"
        }
    }


    override fun alarm_on(textView_alarm:TextView, editText: EditText, string: String){
        textView_alarm.text = string
        textView_alarm.visibility = View.VISIBLE
        editText.setBackgroundResource(R.drawable.edittext_background_red)
    }

    override fun set_pw(editText: EditText, pw: String){
        editText.setText(pw)
        editText.setSelection(editText.text.length)
    }

    override fun button_on(button: Button){
        button.visibility=View.VISIBLE
        button.setBackgroundResource(R.drawable.button_on)
        button.isEnabled =true
    }

    override fun button_off(button: Button) {
        button.visibility=View.GONE
    }

    override fun showing_on(textView_showing:TextView){
        textView_showing.visibility = View.VISIBLE
    }

    override fun showing_off(textView_showing:TextView){
        textView_showing.visibility = View.GONE
    }

    override fun edit_focus(editText: EditText){
        editText.requestFocus()
    }

    override fun layout_change(){
        text_myinfoM_pw_explain.text = "새로운 비밀번호를 입력해주세요."

        edit_myinfoM_pw_oldpw.visibility = View.GONE
        edit_myinfoM_pw_oldpw.isEnabled =false
        text_myinfoM_pw_oldpw_showing.visibility = View.GONE
        text_myinfoM_pw_oldpw_alarm.visibility = View.GONE
        button_off(button_myinfoM_pw_oldpw)

        edit_myinfoM_pw_newpw.visibility = View.VISIBLE
        edit_myinfoM_pw_newcheck.visibility = View.VISIBLE

        edit_myinfoM_pw_newpw.requestFocus()
    }

    override fun passwords_right(){
        text_myinfoM_pw_newcheck_alarm.visibility=View.INVISIBLE
        edit_myinfoM_pw_newcheck.setBackgroundResource(R.drawable.edittext_background)

        if(text_myinfoM_pw_newpw_alarm.visibility==View.INVISIBLE){

            button_myinfoM_pw_newpw.visibility=View.VISIBLE
            button_myinfoM_pw_newpw.setBackgroundResource(R.drawable.button_on)
            button_myinfoM_pw_newpw.isEnabled =true
        }

    }

    override fun passwords_wrong(){
        text_myinfoM_pw_newcheck_alarm.text = "비밀번호가 일치하지 않습니다."
        text_myinfoM_pw_newcheck_alarm.visibility = View.VISIBLE
        edit_myinfoM_pw_newcheck.setBackgroundResource(R.drawable.edittext_background_red)
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun back() {
        onBackPressed()
    }



}