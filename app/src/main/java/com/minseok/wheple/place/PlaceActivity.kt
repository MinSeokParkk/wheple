package com.minseok.wheple.place


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.ReviewActivity
import com.minseok.wheple.select_date_time.SelectDateTimeActivity
import kotlinx.android.synthetic.main.activity_place.*


class PlaceActivity : AppCompatActivity(), PlaceContract.View{

    private lateinit var mPresenter: PlaceContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)


            img_place_back.setOnClickListener {
                onBackPressed()
            }


            ReviewMore1_layout.setOnClickListener {
                val nextIntent = Intent(this, ReviewActivity::class.java)
                startActivity(nextIntent)
            }

            ReviewMore2_layout.setOnClickListener {
              val nextIntent = Intent(this, ReviewActivity::class.java)
              startActivity(nextIntent)
            }

            select_date_time_Button.setOnClickListener {
                    mPresenter.sendPlaceNo()
            }


        PlacePresenter(this, intent.getStringExtra("no"))
        mPresenter.showDetail()

        constraint_place_phone.setOnClickListener{
            mPresenter.calling()
        }


    }



    override fun setPresenter(presenter: PlaceContract.Presenter) {
        this.mPresenter = presenter

    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun setPlace(name : String, address : String, price : String, rating : String, review : String,
                          photo : String, parking : String, shower : String, heating : String, sports : String,
                          introduction :String, guide:String){
        text_place_name.text = name
        text_place_address.text = address
        text_place_price.text = price
        text_place_rating.text = rating
        text_place_review.text = "("+review+")"
        text_place_parking_text.text = "주차 " + parking
        text_place_shower_text.text="샤워 "+shower
        text_place_heating_text.text = heating
        text_place_sports.text = sports
        text_place_introduction.text=introduction
        text_place_guide.text = guide


        Glide.with(this)
            .load(photo)
            .into(img_place_photo)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun noParking(){
        text_place_parking_icon.setTextColor(getColor(R.color.filter_impossible))
        text_place_parking_text.setTextColor(getColor(R.color.filter_impossible))
        constraint_place_parking.setBackgroundResource(R.drawable.rounded_filter_impossible)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun noShower(){
        text_place_shower_icon.setTextColor(getColor(R.color.filter_impossible))
        text_place_shower_text.setTextColor(getColor(R.color.filter_impossible))
        constraint_place_shower.setBackgroundResource(R.drawable.rounded_filter_impossible)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun noHeating(){
        text_place_heating_icon.setTextColor(getColor(R.color.filter_impossible))
        text_place_heating_text.setTextColor(getColor(R.color.filter_impossible))
        constraint_place_heating.setBackgroundResource(R.drawable.rounded_filter_impossible)
    }

    override fun movetoSelect(string: String) {
        val nextIntent = Intent(this, SelectDateTimeActivity::class.java)
        nextIntent.putExtra("no", string)
        startActivity(nextIntent)
    }

}