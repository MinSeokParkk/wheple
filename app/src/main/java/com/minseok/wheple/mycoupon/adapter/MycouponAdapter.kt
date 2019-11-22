package com.minseok.wheple.mycoupon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.minseok.wheple.R
import com.minseok.wheple.myinfo.MycouponItem
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MycouponAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var itemsList:ArrayList<MycouponItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_coupon,parent,false)
        return MycouponViewHolder(rootView)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder:  MycouponViewHolder = holder as  MycouponViewHolder

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

    fun addItems(cpItems:ArrayList<MycouponItem>){
        this.itemsList = cpItems
    }



    inner class MycouponViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var coupMin = itemView.findViewById<TextView>(R.id.text_cpitem_min)
        var coupDisc = itemView.findViewById<TextView>(R.id.text_cpitem_discount)
        var coupName = itemView.findViewById<TextView>(R.id.text_cpitem_name)
        var coupExp = itemView.findViewById<TextView>(R.id.text_cpitem_exp)
        var coupCard = itemView.findViewById<CardView>(R.id.card_coupon)

        fun bind(cp: MycouponItem) {
            coupMin.text = NumberFormat.getNumberInstance(Locale.US).format(cp.min) + "원 이상 장소 대여시"
            coupDisc.text = NumberFormat.getNumberInstance(Locale.US).format(cp.discount)
            coupName.text = cp.name
            coupExp.text = cp.start_date + " ~ " + cp.end_date

            if(cp.name=="우수회원 특별 할인 쿠폰"){
                coupCard.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.black))
            }

        }
    }

}