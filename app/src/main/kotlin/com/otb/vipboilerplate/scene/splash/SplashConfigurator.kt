package com.otb.vipboilerplate.scene.splash

import com.otb.vipboilerplate.common.base.Configurator

/**
 * Created by Mohit Rajput on 7/5/19.
 */
class SplashConfigurator : Configurator<SplashActivity> {
    override fun configure(view: SplashActivity) {
        val router = SplashRouter(view)
        view.router = router
    }
}