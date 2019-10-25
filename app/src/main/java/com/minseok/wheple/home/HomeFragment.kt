package com.minseok.wheple


import android.os.Bundle
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
import android.widget.Spinner
import com.minseok.wheple.home.HomeContract
import com.minseok.wheple.home.HomePresenter
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.home.PlaceItem
import kotlinx.android.synthetic.main.dialog_filter_facility.*
import kotlinx.android.synthetic.main.dialog_filter_location.*
import kotlinx.android.synthetic.main.dialog_filter_location.view.*
import kotlinx.android.synthetic.main.dialog_filter_sports.*
import kotlinx.android.synthetic.main.fragment_home.*



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


            mAlertDialog.dialog_sports_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        //편의시설별 선택 필터
        layout_filter_facility.setOnClickListener {
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.dialog_filter_facility, null)
            val mBuilder = AlertDialog.Builder(this.context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()


            mAlertDialog.dialog_facility_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        //위치별 선택 필터
        layout_filter_location.setOnClickListener {
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.dialog_filter_location, null)
            val mBuilder = AlertDialog.Builder(this.context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()


            val adpater_loc1 = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.spinner_loc) )

            mAlertDialog.spinner_loc1.adapter = adpater_loc1
            mAlertDialog.spinner_loc1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(adpater_loc1.getItem(position).equals("전체")){
                            mAlertDialog.spinner_loc2.isEnabled=false
                        }else if(adpater_loc1.getItem(position).equals("서울")){
                            mAlertDialog.spinner_loc2.isEnabled=true

                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }


            mAlertDialog.dialog_location_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }


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

    fun connect_spinners(spinner: Spinner, arrayAdapter: ArrayAdapter<CharSequence>){

    }


    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
}