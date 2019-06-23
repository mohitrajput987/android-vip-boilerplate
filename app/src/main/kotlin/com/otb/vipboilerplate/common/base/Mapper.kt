package com.otb.vipboilerplate.common.base

/**
 * Created by Mohit Rajput on 5/5/19.
 */
interface Mapper<E, T> {
    fun mapFrom(from: E): T
}