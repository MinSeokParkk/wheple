package com.minseok.wheple.deleteAccountAgree

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

class ChangeTextRed {
    fun changeText(text:String, word1:String, word2:String):SpannableString{
        var spannableString = SpannableString(text)

        val start1 = text.indexOf(word1)
        val end1 = start1 + word1.length

        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#E05151")), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        if(word2!=""){
            val start2 = text.indexOf(word2)
            val end2 = start2 + word2.length

            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#E05151")), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString

    }
}