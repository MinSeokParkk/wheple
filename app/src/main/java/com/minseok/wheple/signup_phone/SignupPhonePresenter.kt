package com.minseok.wheple.signup_phone

import com.minseok.wheple.APIService
import com.minseok.wheple.App
import com.minseok.wheple.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignupPhonePresenter (private val view : SignupPhoneContract.View):SignupPhoneContract.Presenter {


    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }


    override fun inputCheck(phone: String) {
        view.signupbutton(phone.trim().length!=0 && phone.length>9 && phone.length<12 && phone.startsWith("01"))
    }

    override fun phonecheck(phone: String) {
          disposable =
                apiService.serverphonecheck(phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result) }
                    )

    }

    fun showResult(result: Result.Phoneresult){
        println("return ======"+result.result )
        if(result.result.equals("1")){
            view.showToast("이미 가입한 번호입니다.")
            view.wrongInput()

        } else {
            view.phonecheckSuccess(result.result)

        }
    }


}