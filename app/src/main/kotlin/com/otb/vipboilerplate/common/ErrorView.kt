package com.otb.vipboilerplate.common

import com.otb.vipboilerplate.common.base.DisplaysLoadingSpinner
import com.otb.vipboilerplate.common.base.DisplaysMessage

interface ErrorView {

    fun displayInputError(errorViewModel: ErrorViewModel) {
        displayError(errorViewModel)
    }

    fun displayError(errorViewModel: ErrorViewModel) {
        if (this is DisplaysLoadingSpinner) {
            this.dismissLoadingSpinner()
        }
        if (this is DisplaysMessage) {
            this.showMessage(errorViewModel.errorMessage)
        }
    }

    fun displaySessionExpired(errorViewModel: ErrorViewModel)
}