package com.minseok.wheple.deleteAccountBefore

import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DeleteAccountBeforePresenter(private val view : DeleteAccountBeforeContract.View): DeleteAccountBeforeContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun changePw(pw: String) {
        if(pw.contains(" ")){
            view.setPw(pw.replace(" ",""))
        }
        if(pw.trim().length>0){
            view.buttonOn()
        }else{
           view.alarmOn("비밀번호를 입력해주세요.")
        }
    }

    override fun checkPw(pw: String) {
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"oldpw\" :  \""+pw+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("myinfo_check_oldpw.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult(result) }
                )
    }

    fun showResult(result: Result.Connectresult){
        println("return ======"+result.result )
        if(result.result!="1"){
            view.alarmOn("비밀번호가 올바르지 않습니다.")
            view.buttonOff()
            view.wrongInput()

        } else {
            view.nextstage()

        }

    }


}