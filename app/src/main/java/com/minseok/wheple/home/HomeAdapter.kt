package com.minseok.wheple.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.minseok.wheple.home.HomeAdapterContract
import com.minseok.wheple.home.HomeItem

class HomeAdapter(val context: Context) : RecyclerView.Adapter<HomeViewHolder>(), HomeAdapterContract.View,
    HomeAdapterContract.Model {



    private lateinit var homeList: ArrayList<HomeItem>

   // var onItemClickListener: AdapterView.OnItemClickListener?= null

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        homeList[position].let{
            holder?.onBind(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
      = HomeViewHolder(context, parent)

    override fun getItemCount() = homeList.size


    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(homeItems: ArrayList<HomeItem>) {
        this.homeList = homeItems
    }

    override fun clearItem() {
        homeList.clear()
    }
}