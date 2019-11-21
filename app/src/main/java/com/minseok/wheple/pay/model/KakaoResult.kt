package com.minseok.wheple.pay.model

object KakaoResult {
    data class KakaoConnectResult(var tid:String,
                                  var next_redirect_app_url:String,
                                  var android_app_scheme:String)
}