package com.otb.vipboilerplate.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Mohit Rajput on 6/5/19.
 * This class provides methods to show toast messages
 */

object ToastUtils {
    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showShortToast(context: Context, resourceId: Int) {
        Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showLongToast(context: Context, resourceId: Int) {
        Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show()
    }
}
