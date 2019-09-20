package com.minseok.wheple.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.home.PlaceItem


class PlaceAdapter(val itemsList:List<PlaceItem>) : RecyclerView.Adapter<PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return PlaceViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }




}