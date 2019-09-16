package com.minseok.wheple.login


import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.shared.App
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter (private val view : LoginContract.View): LoginContract.Presenter{

    val apiService by lazy {
       APIService.create()
    }

    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun inputCheck(email: String, password: String) {
        view.loginbutton(email.trim().length!=0, password.trim().length!=0)
    }

    override fun login(email: String, password: String)  {



        if(!isEmailValid(email)){
            view.showToast("이메일 형식을 확인해주세요.")
            view.wrongInput(1)
         }else{
            var sending : String
            sending = "{ \"email\" : \""+ email + "\", \r\n" +
                    "\"password\" : \""+password+"\"}"
            println("sending =====" + sending)

            disposable =
                apiService.connect_server("login.php", sending)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result) }
                    )
        }
    }

    fun showResult(result: Result.Connectresult){
        if(result.result.equals("0")){
            view.loginSuccess()

            view.showToast("로그인 성공")

            App.prefs.autologin = true //쉐어드로 로그인 상태 유지시킴

        } else if(result.result.equals("1")){
            view.showToast("가입하지 않은 아이디입니다.")
            view.wrongInput(1)


        } else if(result.result.equals("2")){
            view.showToast("비밀번호가 틀렸습니다.")
            view.wrongInput(2)
        }

        else {
            view.showToast("error")
        }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



}