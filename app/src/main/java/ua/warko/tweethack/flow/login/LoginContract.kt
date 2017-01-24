package ua.warko.tweethack.flow.login

import com.yalantis.base.BasePresenter
import com.yalantis.base.BaseView

/**
 * Created by dmitry on 1/24/17.
 */
class LoginContract {

    interface Presenter : BasePresenter {

        fun login()

    }

    interface View : BaseView {

        fun startTweetActivity()

    }
}