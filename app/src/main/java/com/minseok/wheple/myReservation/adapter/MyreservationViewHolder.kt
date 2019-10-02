package com.minseok.wheple.myReservation.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.myResDetail.MyResDetailActivity
import com.minseok.wheple.myReservation.CheckFinishTime
import com.minseok.wheple.myReservation.MyreservationItem


class MyreservationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var myresName = itemView.findViewById<TextView>(R.id.text_item_myres_name)
    var myresDate = itemView.findViewById<TextView>(R.id.text_item_myres_date)
    var myresTime = itemView.findViewById<TextView>(R.id.text_item_myres_time)
    var reviewbutton = itemView.findViewById<Button>(R.id.button_item_myres_review)
    var state = itemView.findViewById<TextView>(R.id.text_item_myres_state)

    var comparetime = CheckFinishTime()

    fun bind(myresItem: MyreservationItem){
        myresName.text = myresItem.name
        myresDate.text = myresItem.date
        myresTime.text = myresItem.time

        if(myresItem.cancel.equals("t")){
            state.text = "예약취소"
            state.setBackgroundResource(R.color.red)
            reviewbutton.visibility = View.GONE

        }else if(comparetime.comparetime(myresItem.date, myresItem.time)){
            reviewbutton.visibility = View.VISIBLE
            state.text = "이용완료"
            state.setBackgroundResource(R.color.bluegrey)

        }else{
            reviewbutton.visibility = View.GONE
        }

        itemView.setOnClickListener{
//                    Toast.makeText(itemView.context, myresItem.no, Toast.LENGTH_SHORT).show()

            val nextIntent = Intent(itemView.context, MyResDetailActivity::class.java)
              itemView.context.startActivity(nextIntent)

        }



    }

}