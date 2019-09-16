package com.minseok.wheple.signup_others

import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.shared.App
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class SignupOthersPresenter (private val view : SignupOthersContract.View):SignupOthersContract.Presenter {


    val apiService by lazy {
         APIService.create()
    }
    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun inputCheck(email: String, password: String, repassword: String, nickname: String, agreement: Boolean) {
        view.signupbutton(email.trim().length!=0, password.trim().length!=0, repassword.trim().length!=0,
                                nickname.trim().length!=0, agreement)
    }


    override fun signup(email: String, password: String, repassword: String, nickname: String, phone: String) {
        val regex_password = Regex(pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,16}")
        val regex_nickname = Regex(pattern = "[가-힣]{2,8}")

        if(!isEmailValid(email)) {
            view.showToast("이메일 형식을 확인해주세요.")
            view.wrongInput(-1)
        }else if(!regex_password.matches(input = password)) {
            view.showToast("비밀번호를 형식에 맞게 입력해주세요.")
            view.wrongInput(-2)
        }else if(!repassword.equals(password)){
            view.showToast("비밀번호가 같지 않습니다.")
            view.wrongInput(-3)
        }else if(!regex_nickname.matches(input = nickname)){
            view.showToast("닉네임을 형식에 맞게 입력해주세요.")
            view.wrongInput(2)
        }else {
            var sending : String
            sending = "{ \"email\" : \""+ email + "\", \r\n" +
                    "\"password\" : \""+password+"\", \r\n" +
                    "\"nickname\" : \""+nickname+"\", \r\n" +
                    "\"phone\" : \""+phone+"\"}"

            disposable =
                apiService.connect_server("signup.php", sending)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result) }
                    )
        }
    }


    fun showResult(result: Result.Connectresult){
        if(result.result.equals("0")){
            view.signupSuccess()
            view.showToast("회원 가입이 완료되었습니다.")

            App.prefs.autologin = true //쉐어드로 로그인 상태 유지시킴

        } else if(result.result.equals("1")) {
            view.showToast("이미 가입된 이메일 입니다.")
            view.wrongInput(1)

        } else if(result.result.equals("2")){
            view.showToast("이미 사용하고 있는 닉네임입니다.")
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