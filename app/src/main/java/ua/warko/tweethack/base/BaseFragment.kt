package com.yalantis.base

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<out T : BasePresenter, K : ViewDataBinding> : Fragment(), BaseView {

    private var progressDialog: ProgressDialog? = null
    abstract protected val presenter: T
    abstract protected val layoutResourceId: Int
    protected lateinit var binding: K
    abstract val fragmentTag: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        presenter.attachView(this)
        return binding.root
    }

    override fun showError(error: String?) {
        showMessage(error)
    }

    override fun showError(@StringRes strResId: Int) {
        showError(getString(strResId))
    }

    override fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(@StringRes srtResId: Int) {
        showMessage(getString(srtResId))
    }

    override fun showProgress() {
        progressDialog = (progressDialog ?: ProgressDialog(activity)).apply {
            show()
        }
    }

    override fun hideProgress() {
        progressDialog?.let {
            progressDialog?.dismiss()
        }
    }
}
