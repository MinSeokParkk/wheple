package com.minseok.wheple.dibs.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.dibs.DibsItem
import com.minseok.wheple.place.PlaceActivity

class DibsAdpater :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var itemsList:ArrayList<DibsItem>

    var selectReady = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_dibs, parent, false)
        return DibsViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder : DibsViewHolder = holder as DibsViewHolder

        itemsList[position].let{
            itemHolder?.bind(it)
        }


            if (selectReady) {
                itemHolder.selectDibs.visibility = View.VISIBLE

            } else {

                    // 일단 체크된 숫자만 나오게 해놈.////////////////////////////////////
                    if(itemHolder.check){
                        Log.d("dibsFrag", "체크된 숫자 : " +position.toString())
                    }



                itemHolder.selectDibs.visibility = View.GONE
                itemHolder.check = false
                itemHolder.selectDibs.setBackgroundResource(R.color.tp_black)
            }

    }
    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun addItems(placeItems: ArrayList<DibsItem>) {
        this.itemsList = placeItems
    }

    fun readyForDibsCancel(){
        selectReady = true
        notifyAdapter()
    }


    fun rechangeDibs(){
        selectReady = false
        notifyAdapter()
    }


   inner class DibsViewHolder (itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        var imgDibs = itemView.findViewById<ImageView>(R.id.img_dibs)
        var nameDibs = itemView.findViewById<TextView>(R.id.text_dibs_name)
        var addressDibs =  itemView.findViewById<TextView>(R.id.text_dibs_address)
        var priceDibs = itemView.findViewById<TextView>(R.id.text_dibs_price)
        var selectDibs = itemView.findViewById<ConstraintLayout>(R.id.const_dibsitem_select)

       var check = false

        fun bind(dibsItem: DibsItem){
            Glide.with(itemView)
                .load(dibsItem.photo)
                .into(imgDibs)

            nameDibs.text = dibsItem.name
            addressDibs.text = dibsItem.address
            priceDibs.text = dibsItem.price

            itemView.setOnClickListener{
                if(!selectReady){
                    val nextIntent = Intent(itemView.context, PlaceActivity::class.java)
                    nextIntent.putExtra("no", dibsItem.no)
                    itemView.context.startActivity(nextIntent)
                }else{
                    if(!check){
                        check = true
                        selectDibs.setBackgroundResource(R.color.tp_primary)
                    }else{
                        check = false
                        selectDibs.setBackgroundResource(R.color.tp_black)
                    }

                }



            }
        }


    }


}