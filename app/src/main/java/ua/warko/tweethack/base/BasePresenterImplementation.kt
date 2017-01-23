package com.yalantis.base

import android.support.annotation.StringRes
import com.yalantis.manager.SharedPrefManager
import rx.Subscription
import rx.internal.util.SubscriptionList

/**
 * Created by voltazor on 20/03/16.
 */
abstract class BasePresenterImplementation<V : BaseView> : BasePresenter {

    protected lateinit var mSpManager: SharedPrefManager
    protected var mView: V? = null
    private val mSubscriptionList = SubscriptionList()

    /**
     * Attach view to presenter, also here we have subscription
     * for destroy event. On destroy event we should detach view
     * and destroy presenter

     * @param view extend BaseView
     */
    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: BaseView) {
        mView = view as V
        mSpManager = SharedPrefManager.getInstance(view.getContext())
    }

    /**
     * This method adds given rx subscription to the [.mSubscriptionList]
     * which is unsubscribed [.detachView]

     * @param subscription - rx subscription that must be unsubscribed [.detachView]
     */
    protected fun addSubscription(subscription: Subscription) {
        mSubscriptionList.add(subscription)
    }

    protected fun getString(@StringRes strResId: Int): String? {
        return mView?.getContext()?.getString(strResId)
    }

    protected fun getString(@StringRes strResId: Int, vararg formatArgs: Any): String? {
        return mView?.getContext()?.getString(strResId, *formatArgs)
    }

    /**
     * Here we are detaching view and removing and
     * unsubscribing all subscriptions
     */
    override fun detachView() {
        mSubscriptionList.unsubscribe()
        mSubscriptionList.clear()
        mView = null
    }

}
