package com.minseok.wheple.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.retrofit.APIService
import kotlinx.android.synthetic.main.item_home.view.*

class PlaceAdapter(val itemsList:List<PlaceItem>) : RecyclerView.Adapter<PlaceItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return PlaceItemViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }


}