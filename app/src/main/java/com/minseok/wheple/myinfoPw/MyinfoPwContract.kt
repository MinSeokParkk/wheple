package com.minseok.wheple.myinfoPw

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MyinfoPwContract {
    interface View : BaseView<Presenter> {

        fun alarm_on(textView_alarm: TextView, editText: EditText, string: String)
        fun set_pw(editText: EditText, pw: String)
        fun button_on(button: Button)
        fun button_off(button: Button)
        fun showing_on(textView_showing:TextView)
        fun showing_off(textView_showing:TextView)
        fun edit_focus(editText: EditText)
        fun layout_change()
        fun passwords_right()
        fun passwords_wrong()
        fun showToast(string: String)
        fun back()


    }

    interface Presenter : BasePresenter {
       fun changeOldPw(editText: EditText, textView_showing:TextView, textView_alarm: TextView,
                       button: Button, oldpw:String)
        fun checkOldPw(editText: EditText, textView_alarm: TextView, button: Button, oldpw: String)
        fun checkNewPw(editText: EditText,textView_showing: TextView,textView_alarm: TextView,
                       newpw:String, oldpw: String, newcheck: String)
        fun checkNewCheck(editText: EditText,textView_showing: TextView, textView_alarm: TextView,
                          newcheck:String, newpw:String)
        fun saveNewPw(newpw: String)
    }
}