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

    val regex = LoginRegex()

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun inputCheck(email: String, password: String) {
       if(regex.lengthCheck(email)&&regex.lengthCheck(password)){
           view.loginbutton_on()
       } else{
           view.loginbutton_off()
       }
    }

    override fun login(email: String, password: String)  {

        if(!regex.lengthCheck(email)){
            view.showToast("이메일을 입력해주세요.")
            view.wrongInput(1)
        }else if(!regex.lengthCheck(password)){
            view.showToast("비밀번호를 입력해주세요.")
            view.wrongInput(2)
        }else if(!regex.emailcheck(email)){
            view.showToast("이메일 형식을 확인해주세요.")
            view.wrongInput(1)
         }else{
            var sending : String
            sending = "{ \"email\" : \""+ email + "\", \r\n" +
                    "\"password\" : \""+password+"\"}"

            disposable =
                apiService.connect_server("login.php", sending)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result, email) }
                    )
        }
    }

    fun  showResult(result: Result.Connectresult, email: String){

        if(result.result.equals("0")){
            view.loginSuccess()

            App.prefs.autologin = email //쉐어드로 로그인 상태 유지시킴

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





}