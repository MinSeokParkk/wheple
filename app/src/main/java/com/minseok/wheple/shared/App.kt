package com.minseok.wheple.shared

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var prefs : MySharedPreferences
        lateinit var mContext: Context
    }
    /* prefs라는 이름의 MySharedPreferences 하나만 생성할 수 있도록 설정. */

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
        mContext = this
    }

    fun getContext():Context{
        return mContext
    }
}