package com.minseok.wheple.coupon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minseok.wheple.R
import com.minseok.wheple.coupon.CouponActivity
import com.minseok.wheple.coupon.CouponItem
import com.minseok.wheple.reservation.ReservationActivity
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CouponAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var itemsList:ArrayList<CouponItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_coupon,parent,false)
        return CouponViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder: CouponViewHolder = holder as CouponViewHolder

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

    fun addItems(cpItems:ArrayList<CouponItem>){
        this.itemsList = cpItems
    }



    inner class CouponViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var coupMin = itemView.findViewById<TextView>(R.id.text_cpitem_min)
        var coupDisc = itemView.findViewById<TextView>(R.id.text_cpitem_discount)
        var coupName = itemView.findViewById<TextView>(R.id.text_cpitem_name)
        var coupExp = itemView.findViewById<TextView>(R.id.text_cpitem_exp)

        fun bind(cp:CouponItem){
            coupMin.text = NumberFormat.getNumberInstance(Locale.US).format(cp.min) + "원 이상 장소 대여시"
            coupDisc.text = NumberFormat.getNumberInstance(Locale.US).format(cp.discount)
            coupName.text = cp.name
            coupExp.text  = cp.start_date + " ~ "+cp.end_date

            itemView.setOnClickListener {
                Log.d("couponA1", "아이템 클릭 "+ cp.no)

                ReservationActivity.couponchange = true
                ReservationActivity.couponNo = cp.no
                ReservationActivity.couponcount = cp.discount

                 CouponActivity.activity?.finish()
            }
        }
    }
}