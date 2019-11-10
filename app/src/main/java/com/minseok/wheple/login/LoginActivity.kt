package com.minseok.wheple.login


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.main.MainActivity
import com.minseok.wheple.place.PlaceActivity
import com.minseok.wheple.reservation.ReservationActivity
import com.minseok.wheple.signup_phone.SignupPhoneActivity


class LoginActivity : AppCompatActivity(), LoginContract.View{
        private lateinit var mPresenter : LoginContract.Presenter


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)


            login_email_editText.afterTextChanged {
                editTextCheck()
            }

            login_password_editText.afterTextChanged {
                editTextCheck()
            }


            login_Button.setOnClickListener {
                loginUser()

            }

            signup_text.setOnClickListener {  //회원가입할 때
                val nextIntent = Intent(this, SignupPhoneActivity::class.java)
                startActivity(nextIntent)
            }

            img_login_back.setOnClickListener {
                onBackPressed()
            }

            LoginPresenter(this)
        }


        override fun setPresenter(presenter: LoginContract.Presenter) {
            this.mPresenter = presenter
        }

        internal fun loginUser(){

            mPresenter.login(login_email_editText.text.toString(), login_password_editText.text.toString())
        }


    override fun loginSuccess() {
        if(intent.getStringExtra("space")!=null) { //시간 선택 액티비티에서 비로그인 상태로 "예약하기"버튼 눌렀을 때는 예약 액티비티로 보냄
            showToast("로그인 성공!\n예약을 계속 진행해주세요.")

            PlaceActivity.MyClass.res_login_back = true // 로그인 했다고 표시 -> 장소 액티비티로 돌아가면 찜했는 지 여부 다시 확인

            val nextIntent = Intent(this, ReservationActivity::class.java)
            nextIntent.putExtra("space", intent.getStringExtra("space"))
            nextIntent.putExtra("date", intent.getStringExtra("date"))
            nextIntent.putExtra("timeNo", intent.getStringExtra("timeNo"))
            nextIntent.putExtra("timeText", intent.getStringExtra("timeText"))

            startActivity(nextIntent)


        }else if(intent.getStringExtra("dib_place")!=null){//장소액티비티에서 비로그인 상태로 "찜"버튼 눌렀을 때는 장소 액티비티로 돌려보냄.
                PlaceActivity.MyClass.login_back = true // 로그인 했다고 표시 -> 장소를 찜한 상태로 장소액티비티가 다시 시작함.


        }else { // 그 외 사항이면 메인 액티비티(홈 플래그먼트로 시작)로 보냄.
            showToast("로그인 성공!")
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
        finish()

    }

    override fun showToast(string: String) {
         Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun wrongInput(int: Int) {
        if(int==1){ // 이메일이 틀렸을 때
            login_email_editText.requestFocus()
        }else if(int == 2){ //  비번이 틀렸을 때
            login_password_editText.text.clear()
            login_password_editText.requestFocus()
        }

    }


    internal fun editTextCheck(){

        mPresenter.inputCheck(login_email_editText.text.toString(), login_password_editText.text.toString())
    }


    override fun loginbutton_on() {
        login_Button.setBackgroundResource(R.drawable.button_on)
    }

    override fun loginbutton_off() {
        login_Button.setBackgroundResource(R.drawable.button_off)
    }





}