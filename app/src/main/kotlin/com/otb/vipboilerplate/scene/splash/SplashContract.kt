package com.otb.vipboilerplate.scene.splash

import com.otb.vipboilerplate.common.base.BaseView

/**
 * Created by Mohit Rajput on 7/5/19.
 * Contains interfaces of VIP components
 */
class SplashContract {

    interface View : BaseView {

    }

    interface Router {
        fun goToMovieList()
    }
}