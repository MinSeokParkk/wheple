package com.minseok.wheple.deleteAccountBefore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.deleteAccountAgree.DeleteAccountAgreeActivity
import kotlinx.android.synthetic.main.activity_delete_account_before.*

class DeleteAccountBeforeActivity: AppCompatActivity(), DeleteAccountBeforeContract.View {
    private lateinit var mPresenter:  DeleteAccountBeforeContract.Presenter

    override fun setPresenter(presenter:  DeleteAccountBeforeContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account_before)
        mPresenter = DeleteAccountBeforePresenter(this)

        img_del_before_back.setOnClickListener {
            onBackPressed()
        }

        edit_del_before_pw.afterTextChanged {
            text_del_before_alarm.visibility = View.GONE
            edit_del_before_pw.setBackgroundResource(R.drawable.edittext_background)
            buttonOff()
            mPresenter.changePw(edit_del_before_pw.text.toString())
        }

        button_del_before.setOnClickListener {
            mPresenter.checkPw(edit_del_before_pw.text.toString())
        }
    }

    override fun alarmOn(string:String){
        text_del_before_alarm.text = string
        text_del_before_alarm.visibility = View.VISIBLE
        edit_del_before_pw.setBackgroundResource(R.drawable.edittext_background_red)
    }
    override fun setPw(pw:String){
        edit_del_before_pw.setText(pw)
        edit_del_before_pw.setSelection(edit_del_before_pw.text.length)
    }
    override fun buttonOn(){
        button_del_before.visibility=View.VISIBLE
        button_del_before.setBackgroundResource(R.drawable.button_on)
        button_del_before.isEnabled =true
    }

    override fun buttonOff(){
        button_del_before.visibility=View.GONE
    }

    override fun wrongInput(){
        edit_del_before_pw.requestFocus()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun nextstage(){
        val nextIntent = Intent(this, DeleteAccountAgreeActivity::class.java)
        startActivity(nextIntent)

        finish()
    }
}