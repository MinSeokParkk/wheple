package com.minseok.wheple.signup_phone


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.signup_others.SignupOthersActivity

import kotlinx.android.synthetic.main.activity_signup_phone.*

class SignupPhoneActivity : AppCompatActivity(), SignupPhoneContract.View{

    private lateinit var mPresenter : SignupPhoneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_phone)


        signup_phone_editText.afterTextChanged {
            editTextCheck()
        }

        signup_next_Button.setOnClickListener {
            phonecheckServer()
        }

        SignupPhonePresenter(this)
    }

    override fun setPresenter(presenter: SignupPhoneContract.Presenter) {
        this.mPresenter = presenter
    }


    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun phonecheckSuccess(phone : String) {

        val nextIntent = Intent(this, SignupOthersActivity::class.java)
        nextIntent.putExtra("phone", phone)
        startActivity(nextIntent)

    }

    override fun wrongInput() {
           signup_phone_editText.requestFocus()

    }


    internal fun editTextCheck(){

        mPresenter.inputCheck(signup_phone_editText.text.toString())
    }

    internal fun phonecheckServer(){
        mPresenter.phonecheck(signup_phone_editText.text.toString())
    }

    override fun signupbutton_on() {
        signup_next_Button.setBackgroundResource(R.drawable.button_on)
    }

    override fun signupbutton_off() {
        signup_next_Button.setBackgroundResource(R.drawable.button_off)
    }

}