package com.minseok.wheple.filter

import com.minseok.wheple.Filter_data
import com.minseok.wheple.retrofit.APIService
import io.reactivex.disposables.Disposable

class FilterPresenter (private val view : FilterContract.View): FilterContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun get_filterdata() {
        view.setting(Filter_data.soccer, Filter_data.futsal, Filter_data.baseball, Filter_data.basketball,
                    Filter_data.badminton, Filter_data.tennis, Filter_data.pingpong, Filter_data.volleyball,
                    Filter_data.parking, Filter_data.shower, Filter_data.cooling, Filter_data.heating,
                    Filter_data.loc1, Filter_data.loc2)
    }

    override fun adjust(c1:Boolean, c2:Boolean, c3:Boolean, c4:Boolean, c5:Boolean, c6:Boolean,
                         c7:Boolean, c8:Boolean, c9:Boolean, c10:Boolean, c11:Boolean, c12:Boolean,
                         loc1: String, loc2: String){
        Filter_data.soccer = c1
        Filter_data.futsal = c2
        Filter_data.baseball = c3
        Filter_data.basketball = c4
        Filter_data.badminton = c5
        Filter_data.tennis = c6
        Filter_data.pingpong = c7
        Filter_data.volleyball = c8
        Filter_data.parking = c9
        Filter_data.shower = c10
        Filter_data.cooling = c11
        Filter_data.heating = c12
        Filter_data.loc1 = loc1
        Filter_data.loc2 = loc2

        Filter_data.filter_change = true
        view.dismiss()
    }

    override fun reset() {
        Filter_data.soccer = false
        Filter_data.futsal = false
        Filter_data.baseball = false
        Filter_data.basketball = false
        Filter_data.badminton = false
        Filter_data.tennis = false
        Filter_data.pingpong = false
        Filter_data.volleyball = false
        Filter_data.parking = false
        Filter_data.shower = false
        Filter_data.cooling = false
        Filter_data.heating = false
        Filter_data.loc1 = "전체"
        Filter_data.loc2 = " "

        get_filterdata()
    }
}