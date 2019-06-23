package com.otb.vipboilerplate.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Mohit Rajput on 5/4/19.
 * Disposable Collector
 */
class DisposableCollector {
    private val compositeDisposable = CompositeDisposable()

    internal fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    internal fun clearDisposables() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}