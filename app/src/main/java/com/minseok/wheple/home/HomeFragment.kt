package com.minseok.wheple


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import com.minseok.wheple.filter.FilterActivity
import com.minseok.wheple.home.HomeContract
import com.minseok.wheple.home.HomePresenter
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.home.PlaceItem
import kotlinx.android.synthetic.main.dialog_filter_facility.*
import kotlinx.android.synthetic.main.dialog_filter_location.*
import kotlinx.android.synthetic.main.dialog_filter_location.view.*
import kotlinx.android.synthetic.main.dialog_filter_sports.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception
import java.lang.reflect.AccessibleObject.setAccessible





class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var mPresenter : HomeContract.Presenter

    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var placeAdapter: PlaceAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)


        HomePresenter(this)



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        makeRecycler()

        //종목별 선택 필터
        layout_filter_sports.setOnClickListener{
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.dialog_filter_sports, null)
            val mBuilder = AlertDialog.Builder(this.context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            //기존 조건에 맞게 체크박스 세팅
            mPresenter.setfilter_sports(mAlertDialog.check_soccer, mAlertDialog.check_futsal, mAlertDialog.check_baseball, mAlertDialog.check_basketball,
                                  mAlertDialog.check_badminton, mAlertDialog.check_tennis, mAlertDialog.check_pingpong, mAlertDialog.check_volleyball)

            mAlertDialog.dialog_sports_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            mAlertDialog.dialog_sports_apply.setOnClickListener {

                mPresenter.filter_sports(mAlertDialog.check_soccer.isChecked, mAlertDialog.check_futsal.isChecked, mAlertDialog.check_baseball.isChecked,
                    mAlertDialog.check_basketball.isChecked, mAlertDialog.check_badminton.isChecked, mAlertDialog.check_tennis.isChecked,
                    mAlertDialog.check_pingpong.isChecked, mAlertDialog.check_volleyball.isChecked)

                destroyRecycler()
                makeRecycler()

                mPresenter.set_sportsBack() //조건이 있으면 스포츠 필터 레이아웃 배경색 바꿈
                mPresenter.set_homefilterBack() //조건이 있으면 홈필터 레이아웃 배경색 바꿈

                mAlertDialog.dismiss()

            }
        }

        //편의시설별 선택 필터
        layout_filter_facility.setOnClickListener {
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.dialog_filter_facility, null)
            val mBuilder = AlertDialog.Builder(this.context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            //기존 조건에 맞게 체크박스 세팅
            mPresenter.setfilter_facility(mAlertDialog.check_parking, mAlertDialog.check_shower, mAlertDialog.check_cooling, mAlertDialog.check_heating)


            mAlertDialog.dialog_facility_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            mAlertDialog.dialog_facility_apply.setOnClickListener {
                mPresenter.filter_facility(mAlertDialog.check_parking.isChecked, mAlertDialog.check_shower.isChecked,
                    mAlertDialog.check_cooling.isChecked, mAlertDialog.check_heating.isChecked )

                destroyRecycler()
                makeRecycler()

                mPresenter.set_facilityBack() //조건이 있으면 편의시설 필터 레이아웃 배경색 바꿈
                mPresenter.set_homefilterBack() //조건이 있으면 홈필터 레이아웃 배경색 바꿈

                mAlertDialog.dismiss()
            }
        }

        //위치별 선택 필터
        layout_filter_location.setOnClickListener {
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.dialog_filter_location, null)
            val mBuilder = AlertDialog.Builder(this.context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            val adapter_loc1 = ArrayAdapter(activity, R.layout.spinner_item, resources.getStringArray(R.array.spinner_loc) )
            mAlertDialog.spinner_loc1.adapter = adapter_loc1
            mAlertDialog.spinner_loc1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(adapter_loc1.getItem(position).equals("전체")){

                            mAlertDialog.spinner_loc2.isEnabled=false
                            mAlertDialog.spinner_loc2.setBackgroundResource(R.drawable.spinner_background_off)
                            val items = resources.getStringArray(R.array.spinner_loc_all)
                            connect_spinners(mAlertDialog.spinner_loc2, items)

                        }else if(adapter_loc1.getItem(position).equals("서울")){
                            mAlertDialog.spinner_loc2.isEnabled=true
                            mAlertDialog.spinner_loc2.setBackgroundResource(R.drawable.spinner_background)
                            val items = resources.getStringArray(R.array.spinner_loc_seoul)
                            connect_spinners(mAlertDialog.spinner_loc2, items)

                        }else if(adapter_loc1.getItem(position).equals("인천")){
                            mAlertDialog.spinner_loc2.isEnabled=true
                            mAlertDialog.spinner_loc2.setBackgroundResource(R.drawable.spinner_background)
                            val items = resources.getStringArray(R.array.spinner_loc_incheon)
                            connect_spinners(mAlertDialog.spinner_loc2, items)

                        }else if(adapter_loc1.getItem(position).equals("경기")){
                            mAlertDialog.spinner_loc2.isEnabled=true
                            mAlertDialog.spinner_loc2.setBackgroundResource(R.drawable.spinner_background)
                            val items = resources.getStringArray(R.array.spinner_loc_gg)
                            connect_spinners(mAlertDialog.spinner_loc2, items)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }

            val position2 = adapter_loc1.getPosition(mPresenter.get_loc1())
            mAlertDialog.spinner_loc1.setSelection(position2)




            mAlertDialog.dialog_location_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

            mAlertDialog.dialog_location_apply.setOnClickListener {
                println(mAlertDialog.spinner_loc1.selectedItem.toString() + " "+mAlertDialog.spinner_loc2.selectedItem.toString())
                mPresenter.save_loc(mAlertDialog.spinner_loc1.selectedItem.toString(), mAlertDialog.spinner_loc2.selectedItem.toString())

                destroyRecycler()
                makeRecycler()

                mPresenter.set_locationBack() //조건이 있으면 위치 필터 레이아웃 배경색 바꿈
                mPresenter.set_homefilterBack() //조건이 있으면 홈필터 레이아웃 배경색 바꿈

                mAlertDialog.dismiss()
            }
        }

        layout_home_filter.setOnClickListener {
            activity?.let {
                val intent = Intent(it, FilterActivity::class.java)
                it.startActivity(intent)

            }
        }


    }

    override fun onStart() {
        mPresenter.checkChange() // 필터액티비티에서 바꾼 조건 반영
        mPresenter.set_sportsBack() //조건이 있으면 스포츠 필터 레이아웃 배경색 바꿈
        mPresenter.set_facilityBack() //조건이 있으면 편의시설 필터 레이아웃 배경색 바꿈
        mPresenter.set_locationBack() //조건이 있으면 위치 필터 레이아웃 배경색 바꿈
        mPresenter.set_homefilterBack() //조건이 있으면 홈필터 레이아웃 배경색 바꿈
        super.onStart()
    }


    override  fun connectAdapter(){

        recycler_home.adapter = placeAdapter

    }

    override fun setPresenter(presenter: HomeContract.Presenter) {

        this.mPresenter = presenter

    }

    override fun makeRecycler() {
        recycler_home.layoutManager = linearLayoutManager

        placeAdapter = PlaceAdapter()

        mPresenter.getlist(placeAdapter)
    }

    override fun destroyRecycler() {
        recycler_home.layoutManager = null
    }

    override fun setPlaceNumber(string: String){
        text_home_number.text = string
    }


    fun connect_spinners(spinner: Spinner, items:Array<String>){
        val adapter_loc2 =  ArrayAdapter(activity,  R.layout.spinner_item, items)
        spinner.adapter = adapter_loc2

        val position = adapter_loc2.getPosition(mPresenter.get_loc2())
        spinner.setSelection(position)


        try {
            val popup = Spinner::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true

            // Get private mPopup member variable and try cast to ListPopupWindow
            val popupWindow = popup.get(spinner) as android.widget.ListPopupWindow

            popupWindow.height = 365 //높이 설정

        } catch (ex: Exception) {
            when(ex){
                is NoClassDefFoundError, is ClassCastException, is NoSuchFieldException, is IllegalAccessException->{

                }else -> throw ex
            }
        }
    }


    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun set_sports(c1:CheckBox, c2:CheckBox, c3:CheckBox, c4:CheckBox,
                           c5:CheckBox, c6:CheckBox, c7:CheckBox, c8:CheckBox,
                           soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                           badminton:Boolean, tennis:Boolean, pingpong:Boolean, volley:Boolean){
        c1.isChecked = soccer
        c2.isChecked = futsal
        c3.isChecked = baseball
        c4.isChecked = basketball
        c5.isChecked = badminton
        c6.isChecked = tennis
        c7.isChecked = pingpong
        c8.isChecked = volley
    }

    override fun set_facility(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox,
                               parking:Boolean, shower:Boolean, cooling:Boolean, heating:Boolean){
        c1.isChecked = parking
        c2.isChecked = shower
        c3.isChecked = cooling
        c4.isChecked = heating
    }

    override fun filter_sportsBack(change:Boolean){
        if(change){
            layout_filter_sports.setBackgroundResource(R.drawable.rounded_option_on)
        }else{
            layout_filter_sports.setBackgroundResource(R.drawable.rounded_option)
        }

    }

    override fun filter_facilityBack(change:Boolean){
        if(change){
            layout_filter_facility.setBackgroundResource(R.drawable.rounded_option_on)
        }else{
            layout_filter_facility.setBackgroundResource(R.drawable.rounded_option)
        }
    }

    override fun filter_locationBack(change:Boolean){
        if(change){
            layout_filter_location.setBackgroundResource(R.drawable.rounded_option_on)
        }else{
            layout_filter_location.setBackgroundResource(R.drawable.rounded_option)
        }
    }

    override fun homefilter_Back(change:Boolean){
        if(change){
            layout_home_filter.setBackgroundResource(R.drawable.rounded_option_on)
        }else{
            layout_home_filter.setBackgroundResource(R.drawable.rounded_option)
        }
    }

    override fun showNothing(itemsize:Int){
        if(itemsize==0){
            img_home_nothing.visibility = View.VISIBLE
            text_home_nothing.visibility = View.VISIBLE
        }else{
            img_home_nothing.visibility = View.GONE
            text_home_nothing.visibility = View.GONE
        }
    }
}