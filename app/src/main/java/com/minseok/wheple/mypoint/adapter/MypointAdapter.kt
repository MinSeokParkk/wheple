package com.minseok.wheple.mypoint.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.minseok.wheple.R
import com.minseok.wheple.mypoint.MypointItem

class MypointAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var itemsList:ArrayList<MypointItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_mypoint,parent,false)
        return MypointViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder:  MypointViewHolder = holder as  MypointViewHolder

        itemsList[position].let {
            itemHolder?.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter(){
        notifyDataSetChanged()
    }

    fun addItems(cpItems:ArrayList<MypointItem>){
        this.itemsList = cpItems
    }



    inner class MypointViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var mpurpose = itemView.findViewById<TextView>(R.id.text_mypitem_purpose)
        var mpoint = itemView.findViewById<TextView>(R.id.text_mypitem_point)
        var mdate = itemView.findViewById<TextView>(R.id.text_mypitem_date)


        fun bind(mi: MypointItem) {
             mpurpose.text = mi.purpose
             mdate.text = mi.date
            var plusminus = "+"
            if(mi.plus=="f"){
                plusminus = "-"
                mpoint.setTextColor(getColor(itemView.context, R.color.red))
            }
            mpoint.text = plusminus + mi.point


        }
    }
}