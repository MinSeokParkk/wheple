package com.minseok.wheple.reviewEditorPhotoadapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

class DragManageAdapter(adapter: ReviewEditorPhotoAdapter, context: Context, dragDirs:Int, swipeDirs:Int)
    : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {


    val dragAdapter = adapter

    override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, source: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
        dragAdapter.swapItems(source.adapterPosition, target.adapterPosition)
        return true
    }



    override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {}

    override fun getSwipeDirs(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder): Int {
        return 0
    }


    override fun clearView(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        println("데이터 바뀐다~")
        dragAdapter.change()

    }
}