package com.minseok.wheple.login


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.minseok.wheple.MainActivity
import com.minseok.wheple.signup_phone.SignupPhoneActivity


class LoginActivity : AppCompatActivity(),

    LoginContract.View{
        private lateinit var mPresenter : LoginContract.Presenter



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            login_Button.isEnabled = false

             login_email_editText.afterTextChanged {
                editTextCheck()
            }

            login_password_editText.afterTextChanged {
                editTextCheck()
            }


            login_Button.setOnClickListener {
                loginUser()
            }

            signup_text.setOnClickListener {  //회원가입할 때
                val nextIntent = Intent(this, SignupPhoneActivity::class.java)
                startActivity(nextIntent)
            }

            LoginPresenter(this)
        }


        override fun setPresenter(presenter: LoginContract.Presenter) {
            this.mPresenter = presenter
        }

        internal fun loginUser(){

            mPresenter.login(login_email_editText.text.toString(), login_password_editText.text.toString())
        }


    override fun loginSuccess() {

        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)

    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun wrongInput(int: Int) {
        if(int==1){ // 이메일이 틀렸을 때
            login_email_editText.requestFocus()
        }else if(int == 2){ //  비번이 틀렸을 때
            login_password_editText.text.clear()
            login_password_editText.requestFocus()
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

        mPresenter.inputCheck(login_email_editText.text.toString(), login_password_editText.text.toString())
    }

    override fun loginbutton(email: Boolean, password: Boolean) {
        login_Button.isEnabled = email && password
    }





}