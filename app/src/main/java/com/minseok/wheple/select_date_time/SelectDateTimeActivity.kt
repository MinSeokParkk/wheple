package com.minseok.wheple.select_date_time

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.reservation.ReservationActivity
import com.minseok.wheple.login.LoginActivity
import com.minseok.wheple.select_date_time.adapter.RecyclerItemClickListenr
import com.minseok.wheple.select_date_time.adapter.TimeAdapter
import kotlinx.android.synthetic.main.activity_selectdatetime.*
import java.util.*

class SelectDateTimeActivity : AppCompatActivity(), SelectDateTimeContract.View{

    class MyClass{
        companion object{
            var activity: Activity? = null
        }
    }

    private lateinit var mPresenter: SelectDateTimeContract.Presenter

    private val linearLayoutManager by lazy {
        androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
    }
    private lateinit var timeAdapter: TimeAdapter
    var date =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectdatetime)

        reservation_Button.isEnabled = false
        MyClass.activity = this@SelectDateTimeActivity

        val place = intent.getStringExtra("no")
        SelectDateTimePresenter(this)

        reservation_Button.setOnClickListener {
            mPresenter.login_check(place, date,timeAdapter.timeNo.toString(),timeAdapter.timeText)

        }

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val today = Calendar.getInstance()

        datePicker?.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)

        ){  view, year, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1
            var strmonth = month.toString()

            if(month<10){
                strmonth = "0"+strmonth
            }

            var strdayOfMonth = dayOfMonth.toString()
            if(dayOfMonth<10){
                strdayOfMonth = "0"+strdayOfMonth
            }

            date = "$year-"+strmonth+"-"+strdayOfMonth
            recycler_select_time.layoutManager = linearLayoutManager
            timeAdapter = TimeAdapter()

            reservation_Button.isEnabled = false
            reservation_Button.setBackgroundResource(R.drawable.button_off)
            reservation_Button.setText("시간을 선택해주세요")

            mPresenter.sendDate(place, date, timeAdapter)


        }

        datePicker?.minDate = today.timeInMillis+86400000 //하루뒤
        datePicker?.maxDate = today.timeInMillis+2592000000 //30일 뒤

        recycler_select_time.addOnItemTouchListener(RecyclerItemClickListenr(this,recycler_select_time, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                println("숫자는 --------------> "+position.toString())
                buttonChange(80)
            }
            override fun onItemLongClick(view: View?, position: Int) {
                buttonChange(10)
            }
        }))



        img_select_back.setOnClickListener {
            onBackPressed()
        }


    }



    override  fun connectAdapter(){

        recycler_select_time.adapter = timeAdapter

    }

    override fun setPresenter(presenter: SelectDateTimeContract.Presenter) {
        this.mPresenter = presenter

    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun gotoNext(space:String,date:String,  timeNo:String, timeText:String) {
//         Toast.makeText(this, timeAdapter.timeNo.toString()+"  &  "+timeAdapter.timeText, Toast.LENGTH_SHORT).show()

        val nextIntent = Intent(this, ReservationActivity::class.java)
        nextIntent.putExtra("space", space)
        nextIntent.putExtra("date", date)
        nextIntent.putExtra("timeNo", timeNo)
        nextIntent.putExtra("timeText", timeText)

        startActivity(nextIntent)
    }

    override fun gotoLogin(space:String,date:String,  timeNo:String, timeText:String) {



        val builder = AlertDialog.Builder(this)
        builder.setMessage("로그인이 필요한 서비스입니다.\n\n로그인 화면으로 이동하시겠습니까?" +
                "\n")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val nextIntent = Intent(this, LoginActivity::class.java)
            nextIntent.putExtra("space", space)
            nextIntent.putExtra("date", date)
            nextIntent.putExtra("timeNo", timeNo)
            nextIntent.putExtra("timeText", timeText)

            startActivity(nextIntent)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }

        builder.show()



    }

    private fun buttonChange(time:Long){
        Handler().postDelayed({

            println("액티비티에선 "+timeAdapter.checktest.toString())
            if(timeAdapter.checktest){
                reservation_Button.isEnabled = true
                reservation_Button.setBackgroundResource(R.drawable.button_on)
                reservation_Button.setText("예약하기")
            } else{
                reservation_Button.isEnabled = false
            }

        }, time)

    }



}

