package com.minseok.wheple.chatlist

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface ChatlistContract {

    interface View : BaseView<Presenter> {
            fun login_mode()
            fun guest_mode()

    }

    interface Presenter : BasePresenter {
        fun check_preference()

    }
}