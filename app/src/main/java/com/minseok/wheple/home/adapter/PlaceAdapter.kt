package com.minseok.wheple.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.home.PlaceItem


class PlaceAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemsList:ArrayList<PlaceItem>

    private val ITEM = 0
    private val LOADING = 1

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {

        if(viewType==ITEM){
            val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
            return PlaceViewHolder(rootView)

        }else{
            val rootiew = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            return HomeLoadingViewHolder(rootiew)
        }


    }



    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)==ITEM){
//                val placeholder:PlaceViewHolder = holder
            var itemHolder : PlaceViewHolder = holder as PlaceViewHolder

            itemsList[position].let{
                itemHolder?.bind(it)
            }
        }else if (getItemViewType(position)==LOADING){

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

    fun newItems(placeItems: ArrayList<PlaceItem>) {
        this.itemsList.addAll(placeItems)
        notifyItemInserted(itemsList.size-1)
    }

    fun clearItem() {
        itemsList.clear()
    }

    fun addLoadingFooter(){
        isLoadingAdded = true
        add(PlaceItem("","","","","","","",""))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = itemsList.size -1
        val item = getItem(position)

        if(item != null){
            itemsList.remove(item)
            notifyItemRemoved(position)

        }

    }

    fun add(pt:PlaceItem){
        itemsList.add(pt)
        notifyItemInserted(itemsList.size - 1)
    }


    override fun getItemViewType(position: Int): Int {
         if(position == itemsList.size-1 && isLoadingAdded) {
             println("프로그래스바 생깁니다~~~~~~~")
             return  LOADING

        } else return ITEM
    }

    fun getItem(position: Int) : PlaceItem{
        return itemsList.get(position)
    }








}