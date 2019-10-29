package com.minseok.wheple.select_date_time.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_selectdatetime.*
import java.security.AccessController.getContext

class TimeAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private lateinit var itemsList: ArrayList<String>
   private var lastCheckedPosition = -1

    var checktest = false
    var timeNo = -1
    var timeText = ""


    private lateinit var resList: ArrayList<String>

    private lateinit var ingList:ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_select_time, parent, false)
        return TimeViewHolder(rootView)



    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {


        itemsList[position].let {
            holder?.bind(it)
        }
        if(position != lastCheckedPosition && lastCheckedPosition!=-1){

            holder.timeline.isChecked = false
            checktest = true //어차피 시간대가 바꼈으니깐 체크 표시된게 있을 것이다.
        }




    }


    override fun getItemCount(): Int {
        return itemsList.size
    }


    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun addItems(timeItems: ArrayList<String>, reservationList:ArrayList<String>) {
        this.itemsList = timeItems
        this.resList = reservationList

    }

    fun clearItem() {
        itemsList.clear()
    }


   inner class TimeViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

       var timeline = itemView.findViewById<RadioButton>(R.id.radio_time)
       var state = itemView.findViewById<TextView>(R.id.radio_state)



        @SuppressLint("ResourceAsColor")
        fun bind(time: String) {

            timeline.text = time

            if(!resList[0].equals("")) {
               for (i in resList.indices) {

                       if (adapterPosition == Integer.parseInt(resList[i])) {

                           timeline.text = "이미 예약"

                           timeline.isEnabled = false

                           timeline.apply {
                               paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                             }


                       }
                   }


               }


//            if(!ingList[0].equals("")){
//                for(i in ingList.indices){
//                    if (adapterPosition == Integer.parseInt(ingList[i])) {
//
//                        timeline.text = "예약 진행중"
//
//                        timeline.isEnabled = false
//
//                        state.visibility = View.VISIBLE
//                        state.text = "예약 진행중"
//
//
//                    }
//                }
//            }


            if(!timeline.text.equals("이미 예약") && !timeline.text.equals("예약 진행중")){
                timeline.isEnabled = true
                timeline.paintFlags = Paint.ANTI_ALIAS_FLAG

                println("테스트 :"+adapterPosition.toString())
            }else{
                timeline.text = time
            }




            timeline.setOnClickListener{
                 lastCheckedPosition = adapterPosition
                 notifyDataSetChanged()


            }

            timeline.setOnLongClickListener{
                println("어댑터 롱~~~")

                true
            }

            timeline.setOnCheckedChangeListener{buttonView, isChecked ->
                if(timeline.isChecked){
                    //임시

                    checktest = true
                    println("true 작동됩니다~~~")
                    timeNo = position
                    timeText = timeline.text.toString()



                }
                else {
                    checktest = false
                    println("false 작동됩니다~~~")
                }
            }

        }

    }
}

