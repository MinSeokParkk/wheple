package com.minseok.wheple.deleteAccountAgree

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.widget.TextView
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.main.MainActivity
import kotlinx.android.synthetic.main.activity_delete_account_agree.*

class DeleteAccountAgreeActivity : AppCompatActivity(), DeleteAccountAgreeContract.View {
    private lateinit var mPresenter: DeleteAccountAgreeContract.Presenter

    override fun setPresenter(presenter: DeleteAccountAgreeContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account_agree)
        mPresenter = DeleteAccountAgreePresenter(this)

        mPresenter.textRed(text_del_agree_first.text.toString(), "계정 정보는 복구가 불가능","", text_del_agree_first)
        mPresenter.textRed(text_del_agree_second.text.toString(), "쿠폰, 포인트는 모두 소멸","복구할 수 없습니다.", text_del_agree_second)
        mPresenter.textRed(text_del_agree_third.text.toString(), "자동으로 삭제되지 않습니다.","반드시 삭제 후 탈퇴", text_del_agree_third)

        mPresenter.getpoint()

        img_del_agree_back.setOnClickListener {
            onBackPressed()
        }

        check_del_agree.setOnCheckedChangeListener { buttonView, isChecked ->
            mPresenter.checkchange(check_del_agree.isChecked)
        }

        button_del_agree.setOnClickListener {
            mPresenter.delete(check_del_agree.isChecked)
        }
    }

    override fun setText(textview: TextView, text: SpannableString) {
        textview.setText(text)
    }

    override fun setpoint(point:String){
        text_del_agree_point.text = point
    }

    override fun buttonChange(check:Boolean){
        if(check){
            button_del_agree.setBackgroundResource(R.drawable.button_on)
        }else{
            button_del_agree.setBackgroundResource(R.drawable.button_off)
        }
    }

    override fun deleteSuccess(){
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)

        finish()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

}