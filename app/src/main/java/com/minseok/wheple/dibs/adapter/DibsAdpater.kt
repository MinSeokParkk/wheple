package com.minseok.wheple.dibs.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.dibs.DibsContract
import com.minseok.wheple.dibs.DibsItem
import com.minseok.wheple.place.PlaceActivity

class DibsAdpater(private var mpresenter: DibsContract.Presenter) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var itemsList:ArrayList<DibsItem>

    var itemInitial = false
    var selectReady = false


    var deletelist = ArrayList<DibsItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_dibs, parent, false)
        return DibsViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder : DibsViewHolder = holder as DibsViewHolder

        itemsList[position].let{
            itemHolder?.bind(it)
        }

        itemHolder.itemView.setOnClickListener{
            if(!selectReady){
                val nextIntent = Intent(itemHolder.itemView.context, PlaceActivity::class.java)
                nextIntent.putExtra("no", itemsList[position].no)
                itemHolder.itemView.context.startActivity(nextIntent)
            }else{
                if(!itemHolder.check){
                    itemHolder.check = true
                    itemHolder.selectDibs.setBackgroundResource(R.color.tp_primary)
                    itemHolder.checkDibs.setTextColor(getColor(itemHolder.itemView.context, R.color.white))
                    deletelist.add(itemsList[position])
                    Log.d("dibsFrag","deletelist 아이템 추가! -> " + deletelist.toString())
                }else{
                    itemHolder.check = false
                    itemHolder.selectDibs.setBackgroundResource(R.color.tp_black)
                    itemHolder.checkDibs.setTextColor(getColor(itemHolder.itemView.context, R.color.tp_white))
                    deletelist.remove(itemsList[position])
                    Log.d("dibsFrag","deletelist 아이템 뺌 -> " + deletelist.toString())
                }

            }

        }


         if (selectReady) {
             itemHolder.selectDibs.visibility = View.VISIBLE
         } else {

                     deletelist.clear()
                     itemHolder.selectDibs.visibility = View.GONE
                     itemHolder.check = false
                     itemHolder.selectDibs.setBackgroundResource(R.color.tp_black)
                     itemHolder.checkDibs.setTextColor(getColor(itemHolder.itemView.context, R.color.tp_white))

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
        itemInitial = true
    }

    fun readyForDibsCancel(){
        selectReady = true
        notifyAdapter()
    }


    fun rechangeDibs(){
        selectReady = false
        notifyAdapter()
    }

    fun  deleteDibs(){
       selectReady = false



        if(deletelist.size>0){
            var deleteno = ""
            for(i in deletelist){
                deleteno = deleteno+"|"+i.no
            }
            Log.d("dibsFrag","deletelist 확인 : " + deleteno)
            mpresenter.delete(deleteno)

            itemsList.removeAll(deletelist)


            deletelist.clear()
        }
        notifyAdapter()

    }


   inner class DibsViewHolder (itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        var imgDibs = itemView.findViewById<ImageView>(R.id.img_dibs)
        var nameDibs = itemView.findViewById<TextView>(R.id.text_dibs_name)
        var addressDibs =  itemView.findViewById<TextView>(R.id.text_dibs_address)
        var priceDibs = itemView.findViewById<TextView>(R.id.text_dibs_price)
        var selectDibs = itemView.findViewById<ConstraintLayout>(R.id.const_dibsitem_select)
        var checkDibs = itemView.findViewById<TextView>(R.id.text_dibsitem_check)

       var check = false

        fun bind(dibsItem: DibsItem){
            Glide.with(itemView)
                .load(dibsItem.photo)
                .into(imgDibs)

            nameDibs.text = dibsItem.name
            addressDibs.text = dibsItem.address
            priceDibs.text = dibsItem.price


        }


    }


}