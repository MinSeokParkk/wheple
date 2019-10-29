package com.minseok.wheple.select_date_time.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerItemClickListenr(context: Context, recyclerView: androidx.recyclerview.widget.RecyclerView, private val  mListener: OnItemClickListener?) : androidx.recyclerview.widget.RecyclerView.OnItemTouchListener {

    private val mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)

        fun onItemLongClick(view: View?, position: Int)

    }

    init {

        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                println("클릭리스너 숏!")
                return true
            }


            override fun onLongPress(e: MotionEvent) {
                        println("클릭리스너 롱~~~~")

                val childView = recyclerView.findChildViewUnder(e.x, e.y)

                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))

                }
            }




        })
    }

    override fun onInterceptTouchEvent(view: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)

        println("클릭리스너 인터셉트~")
//        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
//            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
//
//        }else{
//           println("클릭 리스너 else ELSE")
//        }
        if (childView != null && mListener != null) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
        }

        return false
    }



    override fun onTouchEvent(view: androidx.recyclerview.widget.RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}}