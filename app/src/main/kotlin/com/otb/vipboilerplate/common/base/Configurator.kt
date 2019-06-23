package com.otb.vipboilerplate.common.base

/**
 * Created by Mohit Rajput on 5/5/19.
 * Parent configurator interface
 */
interface Configurator<T : BaseView> {
    fun configure(view: T)
}