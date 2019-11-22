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
    val regex = SignupOthersRegex()

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun inputCheck(email: String, password: String, repassword: String, nickname: String, agreement: Boolean) {

        if(regex.lengthCheck(email)&&regex.lengthCheck(password)&&regex.lengthCheck(repassword)&&regex.lengthCheck(nickname) && agreement){
            view.signupbutton_on()
        }else{
            view.signupbutton_off()
        }
    }


    override fun signup(email: String, password: String, repassword: String, nickname: String, phone: String, agreement: Boolean) {

        val result = regex.checkRegex(email, password, repassword, nickname)

        if(!regex.lengthCheck(email)){
            view.showToast("이메일을 입력해주세요.")
            view.wrongInput(-1)
        } else if(!regex.lengthCheck(password)){
            view.showToast("비밀번호를 입력해주세요.")
            view.wrongInput(-2)
        } else if(!regex.lengthCheck(repassword)){
            view.showToast("비밀번호 확인을 입력해주세요.")
            view.wrongInput(-3)
        } else if(!regex.lengthCheck(nickname)){
            view.showToast("닉네임을 입력해주세요.")
            view.wrongInput(2)
        } else if(!agreement){
            view.showToast("약관에 동의해주세요.")
            view.wrongInput(3)
        } else if(result == 1) {
            view.showToast("이메일 형식을 확인해주세요.")
            view.wrongInput(-1)
        }else if(result == 2) {
            view.showToast("비밀번호를 형식에 맞게 입력해주세요.")
            view.wrongInput(-2)
        }else if(result == 3){
            view.showToast("비밀번호가 같지 않습니다.")
            view.wrongInput(-3)
        }else if(result == 4){
            view.showToast("닉네임을 형식에 맞게 입력해주세요.")
            view.wrongInput(2)
        }else if(result == -1){
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
                        { result -> showResult(result, email) }
                    )
        }
    }


    fun showResult(result: Result.Connectresult, email: String){
        if(result.result.equals("0")){
            view.signupSuccess()
            view.showToastL("회원가입을 환영합니다~\n가입 환영 쿠폰이 발급되었습니다.")

            App.prefs.autologin = email //쉐어드로 로그인 상태 유지시킴

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


}