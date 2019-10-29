package com.minseok.wheple.filter

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.dialog_filter_facility.*
import java.lang.Exception

class FilterActivity : AppCompatActivity(),FilterContract.View {
    private lateinit var mPresenter:  FilterContract.Presenter

    override fun setPresenter(presenter:  FilterContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        mPresenter = FilterPresenter(this)

        img_fil_back.setOnClickListener {
            onBackPressed()
        }

        mPresenter.get_filterdata()

        button_filter.setOnClickListener {
            mPresenter.adjust(Fcheck_soccer.isChecked, Fcheck_futsal.isChecked, Fcheck_baseball.isChecked,
                    Fcheck_basketball.isChecked,Fcheck_badminton.isChecked,Fcheck_tennis.isChecked,
                    Fcheck_pingpong.isChecked,Fcheck_volleyball.isChecked,Fcheck_parking.isChecked,
                    Fcheck_shower.isChecked,Fcheck_cooling.isChecked,Fcheck_heating.isChecked,
                    Fspinner_loc1.selectedItem.toString(), Fspinner_loc2.selectedItem.toString())
        }

        text_filter_reset.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setMessage("필터를 초기화 하시겠습니까?" +
                    "\n")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                mPresenter.reset()
            }

            builder.setNegativeButton(android.R.string.no){dialog, which ->

            }


            builder.show()

        }
    }



    fun spinner_set(loc1:String, loc2:String){
        val adapter_loc1 = ArrayAdapter(this, R.layout.spinner_item, resources.getStringArray(R.array.spinner_loc) )
        Fspinner_loc1.adapter = adapter_loc1
        Fspinner_loc1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(adapter_loc1.getItem(position).equals("전체")){

                    Fspinner_loc2.isEnabled=false
                    Fspinner_loc2.setBackgroundResource(R.drawable.spinner_background_off)
                    val items = resources.getStringArray(R.array.spinner_loc_all)
                    connect_spinners(Fspinner_loc2, items, loc2)

                }else if(adapter_loc1.getItem(position).equals("서울")){
                    Fspinner_loc2.isEnabled=true
                    Fspinner_loc2.setBackgroundResource(R.drawable.spinner_background)
                    val items = resources.getStringArray(R.array.spinner_loc_seoul)
                    connect_spinners(Fspinner_loc2, items, loc2)

                }else if(adapter_loc1.getItem(position).equals("인천")){
                    Fspinner_loc2.isEnabled=true
                    Fspinner_loc2.setBackgroundResource(R.drawable.spinner_background)
                    val items = resources.getStringArray(R.array.spinner_loc_incheon)
                    connect_spinners(Fspinner_loc2, items, loc2)

                }else if(adapter_loc1.getItem(position).equals("경기")){
                    Fspinner_loc2.isEnabled=true
                    Fspinner_loc2.setBackgroundResource(R.drawable.spinner_background)
                    val items = resources.getStringArray(R.array.spinner_loc_gg)
                    connect_spinners(Fspinner_loc2, items, loc2)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val position2 = adapter_loc1.getPosition(loc1)
        Fspinner_loc1.setSelection(position2)
    }

    fun connect_spinners(spinner: Spinner, items:Array<String>, loc2: String){
        val adapter_loc2 =  ArrayAdapter(this,  R.layout.spinner_item, items)
        spinner.adapter = adapter_loc2

        val position = adapter_loc2.getPosition(loc2)
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


    override fun setting(c1:Boolean, c2:Boolean, c3:Boolean, c4:Boolean, c5:Boolean, c6:Boolean,
                         c7:Boolean, c8:Boolean, c9:Boolean, c10:Boolean, c11:Boolean, c12:Boolean,
                         loc1: String, loc2: String){
        Fcheck_soccer.isChecked = c1
        Fcheck_futsal.isChecked = c2
        Fcheck_baseball.isChecked = c3
        Fcheck_basketball.isChecked = c4
        Fcheck_badminton.isChecked = c5
        Fcheck_tennis.isChecked = c6
        Fcheck_pingpong.isChecked = c7
        Fcheck_volleyball.isChecked = c8
        Fcheck_parking.isChecked = c9
        Fcheck_shower.isChecked = c10
        Fcheck_cooling.isChecked = c11
        Fcheck_heating.isChecked = c12

        spinner_set(loc1, loc2)
    }

    override fun dismiss(){
        onBackPressed()
    }
}