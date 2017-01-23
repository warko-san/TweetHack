package com.yalantis.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import com.yalantis.base.BaseDialogFragment
import ua.warko.tweethack.R

class ProgressDialogFragment : BaseDialogFragment() {

    override fun fragmentTag(): String = ProgressDialogFragment::class.java.simpleName

    private var cancelListener: DialogInterface.OnCancelListener? = null

    fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener) {
        this.cancelListener = onCancelListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = ProgressDialog(context)
        dialog.setMessage(getString(R.string.please_wait))
        dialog.setCanceledOnTouchOutside(false)
        dialog.isIndeterminate = true
        return dialog
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        if (cancelListener != null) {
            cancelListener!!.onCancel(dialog)
        }
    }

    companion object {

        fun newInstance(): ProgressDialogFragment {
            return ProgressDialogFragment()
        }
    }

}
