package com.minseok.wheple.place


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.login.LoginActivity
import com.minseok.wheple.map.MapActivity
import com.minseok.wheple.place.adapter.PlacePhotoViewPagerAdapter
import com.minseok.wheple.review.ReviewActivity
import com.minseok.wheple.place.adapter.PlaceReviewAdapter
import com.minseok.wheple.select_date_time.SelectDateTimeActivity
import kotlinx.android.synthetic.main.activity_place.*


class PlaceActivity : AppCompatActivity(), PlaceContract.View{

    private lateinit var mPresenter: PlaceContract.Presenter
    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var prAdapter:PlaceReviewAdapter
    private var mydibs: Boolean = false

    class MyClass{
        companion object{
            var login_back : Boolean = false
            var res_login_back :Boolean = false
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)


            img_place_back.setOnClickListener {
                onBackPressed()
            }


            layout_place_reviewmore.setOnClickListener {
                  mPresenter.review_more(text_place_rating.text.toString(), text_place_review.text.toString())
            }

            layout_place_reviewmore2.setOnClickListener {
                   mPresenter.review_more(text_place_rating.text.toString(), text_place_review.text.toString())
           }

          layout_place_reviewmore3.setOnClickListener {
                   mPresenter.review_more(text_place_rating.text.toString(), text_place_review.text.toString())
           }


            select_date_time_Button.setOnClickListener {
                    mPresenter.sendPlaceNo()
            }

        layout_place_showmap.setOnClickListener {
            mPresenter.show_map()
        }



        PlacePresenter(this, intent.getStringExtra("no"))
        mPresenter.showDetail()

        makeRecycler()

        constraint_place_phone.setOnClickListener{
            mPresenter.calling()
        }

        constraint_place_message.setOnClickListener {
            mPresenter.messaging()
        }

        scroll_place.viewTreeObserver.addOnScrollChangedListener { //스크롤뷰 위치 변화 감지
           mPresenter.topplacename(scroll_place.scrollY) // 스크롤 뷰 y값 전송
        }


        text_place_dibs.setOnClickListener {

            mPresenter.checkDibs(mydibs)
        }


    }

    override fun onStart() {
        if(MyClass.login_back){ //비로그인 상태로 찜하기를 누른 후 로그인 하고 다시 돌아온 상태면

            // 이미 찜 한 상태로 바꿔놓기
            mPresenter.checkDibs(mydibs)

        }

        if(MyClass.res_login_back){
            mPresenter.checkDibs_before()
        }
        super.onStart()

    }



    override fun setPresenter(presenter: PlaceContract.Presenter) {
        this.mPresenter = presenter

    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setPlace(name : String, address : String, price : String, rating : String, review : String,
                          photos: Array<String>, parking : String, shower : String, heating : String, sports : String,
                          introduction :String, guide:String, dibs:Boolean){
        text_place_name.text = name
        text_place_address.text = address
        text_place_price.text = price
        text_place_rating.text = rating
        text_place_review.text = review
        text_place_parking_text.text = "주차 " + parking
        text_place_shower_text.text="샤워 "+shower
        text_place_heating_text.text = heating
        text_place_sports.text = sports
        text_place_introduction.text=introduction
        text_place_guide.text = guide
        text_place_placenametop.text = name
        text_place_ratingB.text = rating
        text_place_reviewB.text = review

        mydibs = dibs
        if(mydibs){ // 이미 찜 한거면
            text_place_dibs.text = "\uf004"
            text_place_dibs.setTextColor(getColor(R.color.red))
        }else{
            text_place_dibs.text = "\uf08a"
            text_place_dibs.setTextColor(getColor(R.color.black))
        }


        val viewPagerAdapter = PlacePhotoViewPagerAdapter(photos)
        viewPager_place.adapter = viewPagerAdapter

        //인디케이터 설정
        makingDots(viewPagerAdapter)

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

    override fun toptextVisibility(state:Int){
        text_place_placenametop.visibility = state
    }

    fun makeRecycler(){
        recycler_place_review.layoutManager = linearLayoutManager
        prAdapter = PlaceReviewAdapter()

        mPresenter.getReview(prAdapter, intent.getStringExtra("no"))
    }

    override fun get_base_url() :String{
        return getString(R.string.base_url)
    }

    override fun connectAdapter(){
        recycler_place_review.adapter = prAdapter
    }

    override fun no_review(){
        const_place_review.visibility = View.GONE
        layout_place_reviewmore3.visibility = View.GONE
        ratingbar_rv.rating = 0f
        icon_more_review.visibility = View.GONE
        text_place_rating.visibility = View.GONE
        layout_place_reviewmore.isEnabled = false

    }

    override fun gotoReview(placeNO:String, rating: String, review: String){
        val nextIntent = Intent(this, ReviewActivity::class.java)
        nextIntent.putExtra("no", placeNO)
        nextIntent.putExtra("rating", rating)
        nextIntent.putExtra("review", review)
        startActivity(nextIntent)
    }

    override fun ask_message(phone:String){
        val smsIntent = Intent(android.content.Intent.ACTION_VIEW)
        smsIntent.setType("vnd.android-dir/mms-sms")
        smsIntent.putExtra("address", phone)
        smsIntent.putExtra("sms_body","웨플 보고 연락드립니다. ")
        startActivity(smsIntent)
    }

    override fun ask_phone(phone: String){
        val call = Uri.parse("tel:"+phone)
        val phoneIntent = Intent(Intent.ACTION_DIAL, call)
        startActivity(phoneIntent)
    }

    fun makingDots(viewPagerAdapter : PlacePhotoViewPagerAdapter){

        val dotscount = viewPagerAdapter.count
        val dots = arrayOfNulls<ImageView>(dotscount)

        for(i in 0..dotscount-1){
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.non_active_dot))

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            params.setMargins(3,0,3,0)

            SliderDots_place.addView(dots[i],params)
        }

        dots[0]!!.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.active_dot))


        viewPager_place.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {

                println("바뀌나요? + "+dotscount.toString())
                for(i in 0..dotscount-1){

                    dots[i]!!.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot))
                }
                dots[position]!!.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.active_dot))

            }

        })

    }

    override fun gotoMap(no:String){
        val nextIntent = Intent(this, MapActivity::class.java)
        nextIntent.putExtra("name", text_place_name.text.toString())
        nextIntent.putExtra("address", text_place_address.text.toString())
        startActivity(nextIntent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun changeHeart(result: Boolean) {
        mydibs = result
        if(mydibs){ // 찜 눌렀으면
            text_place_dibs.text = "\uf004"
            text_place_dibs.setTextColor(getColor(R.color.red))
        }else{ //찜 취소 눌렀으면
            text_place_dibs.text = "\uf08a"
            text_place_dibs.setTextColor(getColor(R.color.black))
        }
    }

    override fun gotoLogin(no_here: String){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("로그인이 필요한 서비스입니다.\n\n로그인 화면으로 이동하시겠습니까?" +
                "\n")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val nextIntent = Intent(this, LoginActivity::class.java)
            nextIntent.putExtra("dib_place", no_here)


            startActivity(nextIntent)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }

        builder.show()
    }



}