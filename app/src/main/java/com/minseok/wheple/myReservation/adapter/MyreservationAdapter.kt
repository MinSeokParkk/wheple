package com.minseok.wheple.myReservation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.myReservation.MyreservationItem

class MyreservationAdapter : RecyclerView.Adapter<MyreservationViewHolder>() {


    private lateinit var itemsList:ArrayList<MyreservationItem>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyreservationViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_my_reservation, parent, false)
        return MyreservationViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MyreservationViewHolder, position: Int) {
        itemsList[position].let{
            holder?.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun addItems(myreservationItems: ArrayList<MyreservationItem>) {
        this.itemsList = myreservationItems
    }

    fun clearItem() {
        itemsList.clear()
    }
}