package com.yalantis.base

import android.content.Context
import android.support.annotation.StringRes

interface BaseView {

    fun getContext(): Context

    fun showProgress()

    fun hideProgress()

    fun showError(@StringRes strResId: Int)

    fun showError(error: String?)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String?)

}
