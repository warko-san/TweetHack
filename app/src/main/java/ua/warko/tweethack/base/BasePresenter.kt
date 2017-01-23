package com.yalantis.base

interface BasePresenter {

    fun attachView(view: BaseView)

    fun detachView()

}
