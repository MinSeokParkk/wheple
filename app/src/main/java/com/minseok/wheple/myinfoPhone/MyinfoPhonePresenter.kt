package com.minseok.wheple.myinfoPhone

import com.minseok.wheple.myinfo.MyinfoActivity
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyinfoPhonePresenter (private val view : MyinfoPhoneContract.View):MyinfoPhoneContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var regex = MyinfoPhoneRegex()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun changePhone(phone: String, oldPhone: String) {
        if(phone.length>0){
            val phoneresult = regex.checkPhone(phone)
            if(phoneresult!=null){
                view.alarmOn(phoneresult)
            }else{
                if(phone!=oldPhone){
                    view.buttonOn()
                }else{
                    view.alarmOn("현재 전화번호와 동일합니다.")
                }
            }

        }else{
            view.deleteOff()
            view.alarmOn("전화번호를 입력해주세요.")
        }
    }

    override fun savePhone(phone: String) {
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"phone\" : \""+phone+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("myinfo_save_phone.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult(result) }
                )
    }

    fun showResult(result: Result.Connectresult){
        println("return ======"+result.result )
        if(result.result.equals("1")){
            view.alarmOn("이미 등록된 전화번호입니다.")
            view.buttonOff()
            view.wrongInput()

        } else {
            view.showToast("전화번호가 변경되었습니다.")

            MyinfoActivity.MyClass.change = true
            view.back()

        }
    }


}