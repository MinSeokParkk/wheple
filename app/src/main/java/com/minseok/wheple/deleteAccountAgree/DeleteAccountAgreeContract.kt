package com.minseok.wheple.deleteAccountAgree

import android.text.SpannableString
import android.widget.TextView
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface DeleteAccountAgreeContract {

    interface View : BaseView<Presenter> {
        fun setText(textview:TextView, text:SpannableString)
        fun setpoint(point:String)
        fun buttonChange(check:Boolean)
        fun deleteSuccess()
        fun showToast(string: String)


    }

    interface Presenter : BasePresenter {
        fun textRed(text:String, word1:String, word2:String, textView: TextView)
        fun getpoint()
        fun checkchange(check: Boolean)
        fun delete(check: Boolean)
    }

}