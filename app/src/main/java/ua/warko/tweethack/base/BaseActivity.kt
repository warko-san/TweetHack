package com.yalantis.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.yalantis.dialog.ProgressDialogFragment

abstract class BaseActivity<out T : BasePresenter, K : ViewDataBinding> : AppCompatActivity(), BaseView {

    private var mProgressDialog: ProgressDialogFragment? = null
    abstract protected val presenter: T
    abstract protected val layoutResourceId: Int
    protected lateinit var binding: K

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        presenter.attachView(this)
    }

    override fun setContentView(layoutId: Int) {
        super.setContentView(layoutId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        mProgressDialog = ProgressDialogFragment.newInstance()

        if (!mProgressDialog?.isAdded!!) {
            mProgressDialog?.show(supportFragmentManager)
        }
    }

    override fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog!!.isAdded) {
            mProgressDialog?.dismiss()
        }
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = this.currentFocus
        view?.let {
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    @JvmOverloads protected fun setFragment(fragment: Fragment, containerId: Int, needBackStack: Boolean = false) {
        val oldFragment = supportFragmentManager.findFragmentById(containerId)

        val transaction = supportFragmentManager.beginTransaction()
        oldFragment?.let {
            transaction.detach(oldFragment)
        }
        transaction.attach(fragment)
        transaction.replace(containerId, fragment)
        if (needBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
