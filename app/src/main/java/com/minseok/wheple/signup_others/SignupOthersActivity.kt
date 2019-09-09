package com.minseok.wheple.signup_others


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.minseok.wheple.MainActivity
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_signup_others.*



class SignupOthersActivity : AppCompatActivity(),
 SignupOthersContract.View{

    private lateinit var mPresenter : SignupOthersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_others)

        signup_finish_Button.isEnabled = false


        signup_email_editText.afterTextChanged {
            editTextCheck()
        }

        signup_password_editText.afterTextChanged {
            editTextCheck()
        }

        signup_repassword_editText.afterTextChanged {
            editTextCheck()
        }

        signup_nickname_editText.afterTextChanged {
            editTextCheck()
        }

        signup_agree_checkBox.setOnCheckedChangeListener{ buttonView, isChecked ->
            editTextCheck()
        }

        signup_finish_Button.setOnClickListener {
                signupUser()
        }

        SignupOthersPresenter(this)

    }

    override fun setPresenter(presenter: SignupOthersContract.Presenter) {
        this.mPresenter = presenter
    }

    internal fun signupUser(){

        mPresenter.signup(
            signup_email_editText.text.toString(),
            signup_password_editText.text.toString(),
            signup_repassword_editText.text.toString(),
            signup_nickname_editText.text.toString(),
            intent.getStringExtra("phone")
        )
    }

    override fun signupSuccess() {

        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)

    }


    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun wrongInput(int: Int) {
        if(int==-1) { // 이메일 형식 오류
            signup_email_editText.requestFocus()
        }else if(int==-2) { //비밀번호 형식 오류
            signup_password_editText.text.clear()
            signup_password_editText.requestFocus()
        }else if(int==-3){ //비밀번호 확인 오류
            signup_repassword_editText.requestFocus()
        }else if(int==1){ // 1이면 이메일 중복
            signup_email_editText.text.clear()
            signup_email_editText.requestFocus()
        } else if(int == 2){ // 2면 닉네임 중복
            signup_nickname_editText.text.clear()
            signup_nickname_editText.requestFocus()
        }

    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    internal fun editTextCheck(){

        mPresenter.inputCheck(signup_email_editText.text.toString(), signup_password_editText.text.toString(), signup_repassword_editText.text.toString(),
                              signup_nickname_editText.text.toString(), signup_agree_checkBox.isChecked)
    }

    override fun signupbutton(email: Boolean, password: Boolean, repassword:Boolean, nickname: Boolean, agreement: Boolean) {
        signup_finish_Button.isEnabled = email && password && repassword && nickname && agreement
    }



}