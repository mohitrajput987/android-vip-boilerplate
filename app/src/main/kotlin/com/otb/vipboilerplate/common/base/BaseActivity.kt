package com.otb.vipboilerplate.common.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.otb.vipboilerplate.common.showLongToast
import io.sentry.Sentry

/**
 * Created by Mohit Rajput on 5/5/19.
 * Parent activity of all other activities which can be used for generic purpose
 * i.e. tracking screen events
 */
open class BaseActivity : AppCompatActivity(), DisplaysLoadingSpinner, DisplaysMessage {
    protected lateinit var TAG: String
    protected lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = localClassName
        context = this
    }

    override fun onStart() {
        super.onStart()
        Sentry.capture("$TAG started")
    }

    override fun onStop() {
        super.onStop()
        Sentry.capture("$TAG stopped")
    }

    override fun showLoadingSpinner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoadingSpinner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        showLongToast(message)
    }
}