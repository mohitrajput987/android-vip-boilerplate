package com.otb.vipboilerplate

import android.app.Application
import io.sentry.Sentry
import io.sentry.android.AndroidSentryClientFactory


/**
 * Created by Mohit Rajput on 26/5/19.
 */
class VipApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Sentry.init(BuildConfig.SENTRY_DSN, AndroidSentryClientFactory(this))
    }
}