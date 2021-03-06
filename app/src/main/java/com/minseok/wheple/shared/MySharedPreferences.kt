package com.minseok.wheple.shared

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences (context: Context) {

    val PREFS_FILENAME = "currentuser"
    val PREF_KEY_MY_EDITTEXT = "autologin"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */

//    var autologin: Boolean
//        get() = prefs.getBoolean(PREF_KEY_MY_EDITTEXT, false)
//        set(value) = prefs.edit().putBoolean(PREF_KEY_MY_EDITTEXT,  value).apply()
    /*  set(value) 실행 시 value로 값을 대체한 후 저장 */


    var autologin: String
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT,  value).apply()

    val editor: SharedPreferences.Editor = prefs.edit()

    fun logout(){
        editor.clear()
        editor.commit()
    }


}