package com.minseok.wheple.home

interface HomeAdapterContract {
    interface View{

        fun notifyAdapter(){

        }
    }
    interface Model{

        fun addItems(homeItems: ArrayList<HomeItem>)

        fun clearItem()
    }
}