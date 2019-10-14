package com.minseok.wheple.reviewEditorPhotoadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class DragManageAdapter(adapter: ReviewEditorPhotoAdapter, context: Context, dragDirs:Int, swipeDirs:Int)
    : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {


    val dragAdapter = adapter

    override fun onMove(recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        dragAdapter.swapItems(source.adapterPosition, target.adapterPosition)
        return true
    }



    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return 0
    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        println("데이터 바뀐다~")
        dragAdapter.change()

    }
}