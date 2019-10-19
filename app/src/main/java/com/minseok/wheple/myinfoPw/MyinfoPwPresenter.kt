package com.minseok.wheple.myinfoPw

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.minseok.wheple.myinfo.MyinfoActivity
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyinfoPwPresenter (private val view : MyinfoPwContract.View):MyinfoPwContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var regex = MyinfoPwRegex()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun changeOldPw(editText: EditText, textView_showing: TextView,
                             textView_alarm: TextView, button: Button, oldpw:String) {
        if(oldpw.trim().length>0){
            view.showing_on(textView_showing)
            if(oldpw.contains(" ")){
                view.set_pw(editText,oldpw.replace(" ",""))
            }
            view.button_on(button)


        }else{
            view.showing_off(textView_showing)
            if(oldpw.contains(" ")){
                view.set_pw(editText,oldpw.replace(" ",""))
            }
            view.alarm_on(textView_alarm,editText,"비밀번호를 입력해주세요.")
        }
    }

    override fun checkOldPw(editText: EditText, textView_alarm: TextView,
                            button: Button, oldpw: String) {
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"oldpw\" : \""+oldpw+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("myinfo_check_oldpw.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult_oldpw(result,editText,
                                                 textView_alarm, button) }
                )
    }

    fun showResult_oldpw(result: Result.Connectresult, editText: EditText,
                         textView_alarm: TextView, button: Button){
        println("return ======"+result.result )
        if(result.result!="1"){
            view.alarm_on(textView_alarm, editText, "비밀번호가 올바르지 않습니다.")
            view.button_off(button)
            view.edit_focus(editText)

        } else {
            view.layout_change()

        }

    }

    override fun checkNewPw(editText: EditText, textView_showing: TextView,
                            textView_alarm: TextView, newpw: String, oldpw: String, newcheck:String) {
         if(newpw.trim().length>0){
             view.showing_on(textView_showing)
             if( newpw.contains(" ")){
                 view.set_pw(editText, newpw.replace(" ",""))
             }

             val pwresult = regex.checkPw(newpw.trim())
             if(pwresult!=null){
                 view.alarm_on(textView_alarm,editText, pwresult)

                 if(newcheck.length>0){
                     passwords_check(newpw.trim(), newcheck)
                 }

             }else{
                 if (newpw.trim()==oldpw){
                     view.alarm_on(textView_alarm,editText,"기존 비밀번호와 동일합니다.")

                     if(newcheck.length>0){
                         passwords_check(newpw.trim(), newcheck)
                     }

                 }else{
                     if(newcheck.length>0){
                         passwords_check(newpw.trim(), newcheck)
                     }

                 }

             }

         }else{
             view.showing_off(textView_showing)
             if( newpw.contains(" ")){
                 view.set_pw(editText, newpw.replace(" ",""))
             }
             view.alarm_on(textView_alarm,editText,"비밀번호를 입력해주세요.")

         }
    }

    override fun checkNewCheck(editText: EditText, textView_showing: TextView,textView_alarm: TextView,
                                newcheck: String, newpw: String) {
        if(newcheck.trim().length>0){
            view.showing_on(textView_showing)
            if( newcheck.contains(" ")){
                view.set_pw(editText, newcheck.replace(" ",""))
            }


            passwords_check(newpw, newcheck.trim())


        }else{
            view.showing_off(textView_showing)
            if( newcheck.contains(" ")){
                view.set_pw(editText, newcheck.replace(" ",""))
            }
                view.alarm_on(textView_alarm,editText,"비밀번호 확인을 입력해주세요.")
                    }
    }

    fun passwords_check(newpw: String, newcheck: String){
        if(newpw==newcheck){
                view.passwords_right()
        }else{
            view.passwords_wrong()
        }
    }

    override fun saveNewPw(newpw: String) {
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"newpw\" : \""+newpw+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("myinfo_save_newpw.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult_save() }
                )
    }

    fun showResult_save(){

            view.showToast("비밀번호가 변경되었습니다.")

            MyinfoActivity.MyClass.change = true
            view.back()


    }


}