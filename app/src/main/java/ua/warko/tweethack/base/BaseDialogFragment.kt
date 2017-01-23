package com.yalantis.base

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatDialogFragment

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    abstract fun fragmentTag(): String

    fun show(manager: FragmentManager) {
        super.show(manager, fragmentTag())
    }

}
