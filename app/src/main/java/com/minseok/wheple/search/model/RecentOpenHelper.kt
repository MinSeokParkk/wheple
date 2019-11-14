package com.minseok.wheple.search.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecentOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "WHEPLE.db"

        private val COLUMN_ID = "Id"
        private val TABLE_NAME = "RecentSearch"
        private val COLUMN_NAME = "Name"
        private val COLUMN_NUMBER = "Place_no"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE $TABLE_NAME ( $COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_NAME TEXT, $COLUMN_NUMBER TEXT)")
        db!!.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

   fun addSearching(name:String, no:String){
       val db = this.writableDatabase
       val cursorB = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = '" + name + "' AND $COLUMN_NUMBER =  '" + no + "'", null)
       if(cursorB.count>0){
           //이미 검색한 목록이 있으면 지우기
           db.execSQL("DELETE FROM $TABLE_NAME WHERE Name = '" + name + "'")
       }

       val values = ContentValues()
       values.put(COLUMN_NAME, name)
       values.put(COLUMN_NUMBER, no)

       db.insert(TABLE_NAME, null, values)

       val cursorE = db.rawQuery("SELECT * FROM $TABLE_NAME",null)
       if(cursorE.count>5){
           // 최근 검색 목록 사이즈가 6개가 될 경우 오래된 검색어 하나 지우기
           db.execSQL("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = (SELECT min($COLUMN_ID) FROM $TABLE_NAME)")
       }
       db.close()

   }

    val allSearching:ArrayList<RecentSearch>
        get(){
            val  lstSearching = ArrayList<RecentSearch>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC" +
                    ""
            val db =  this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler,null)
            if(cursor.moveToFirst()){
                do{
                    val recentSearch = RecentSearch()
                    recentSearch.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    recentSearch.no = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER))

                    lstSearching.add(recentSearch)

                }while (cursor.moveToNext())
            }
            db.close()
            return  lstSearching
        }

    fun deleteAll(){ //전체 지우기
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }

    fun deleteSearching(name:String, no:String){ //하나만 지우기
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME WHERE Name = '" + name + "'")
        db.close()
    }


}