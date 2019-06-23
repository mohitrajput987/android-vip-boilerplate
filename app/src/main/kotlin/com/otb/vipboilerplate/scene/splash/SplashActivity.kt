package com.otb.vipboilerplate.scene.splash

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import com.otb.vipboilerplate.R
import com.otb.vipboilerplate.common.base.BaseActivity
import com.otb.vipboilerplate.utils.NetworkUtils


class SplashActivity : BaseActivity(), SplashContract.View {
    internal lateinit var router: SplashContract.Router
    private val SPLASH_DISPLAY_TIME_IN_MILLIS = (4 * 1000).toLong()
    private var handler: Handler? = null
    private val runnable = Runnable { router.goToMovieList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        SplashConfigurator().configure(this)
        if (NetworkUtils.isNetworkAvailable(context)) {
            onInternetConnected();
        } else {
            openDialogOnNoInternet();
        }
    }

    private fun openDialogOnNoInternet() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.no_network)
        builder.setMessage(R.string.no_internet_connected)
        builder.setCancelable(false)
        builder.setPositiveButton(context.getString(R.string.retry), null)

        builder.setNegativeButton(
            context.getString(R.string.exit)
        ) { dialog, which -> finish() }

        val alertDialog = builder.create()

        alertDialog.setOnShowListener(DialogInterface.OnShowListener {
            val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    onInternetConnected()
                    alertDialog.dismiss()
                }
            }
        })
        alertDialog.show()
    }

    private fun onInternetConnected() {
        startHandler()
    }

    override fun getResourcesInstance(): Resources {
        return resources
    }

    private fun startHandler() {
        handler = Handler()
        handler?.postDelayed(runnable, SPLASH_DISPLAY_TIME_IN_MILLIS)
    }

    private fun stopHandler() {
        if (handler != null) {
            handler?.removeCallbacks(runnable)
            handler = null
        }
    }

    override fun finish() {
        stopHandler()
        super.finish()
    }
}