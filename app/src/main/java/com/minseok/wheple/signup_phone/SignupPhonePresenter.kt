package com.minseok.wheple.signup_phone

import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignupPhonePresenter (private val view : SignupPhoneContract.View):SignupPhoneContract.Presenter {


    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null
    val regex = SignupPhoneRegex()

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }


    override fun inputCheck(phone: String) {

        if(regex.phonecheck(phone)){
            view.signupbutton_on()
        }else{
            view.signupbutton_off()
        }
    }

    override fun phonecheck(phone: String) {
        if(!regex.emptycheck(phone)){
            view.showToast("휴대전화 번호를 입력해주세요.")
            view.wrongInput()
        }
        else if(!regex.phonecheck(phone)){
            view.showToast("휴대전화 번호 형식을 다시 확인해주세요.")
            view.wrongInput()
        }else {
            var sending: String
            sending = "{ \"phone\" : \"" + phone + "\"}"
            println("sending ===   " + sending)
            disposable =
                apiService.connect_server("phonecheck.php", sending)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result) }
                    )
        }
    }

    fun showResult(result: Result.Connectresult){
        println("return ======"+result.result )
        if(result.result.equals("1")){
            view.showToast("이미 가입한 번호입니다.")
            view.wrongInput()

        } else {
            view.phonecheckSuccess(result.result)

        }
    }


}