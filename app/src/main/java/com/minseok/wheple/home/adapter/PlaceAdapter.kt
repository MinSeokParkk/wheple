package com.minseok.wheple.home.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.home.PlaceItem


class PlaceAdapter : RecyclerView.Adapter<PlaceViewHolder>() {

    private lateinit var itemsList:ArrayList<PlaceItem>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return PlaceViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
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

    fun addItems(placeItems: ArrayList<PlaceItem>) {
        this.itemsList = placeItems
    }

    fun clearItem() {
        itemsList.clear()
    }





}