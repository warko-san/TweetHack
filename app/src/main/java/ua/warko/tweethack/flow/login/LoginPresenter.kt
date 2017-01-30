package ua.warko.tweethack.flow.login

import com.yalantis.base.BasePresenterImplementation

/**
 * Created by dmitry on 1/24/17.
 */
class LoginPresenter : BasePresenterImplementation<LoginContract.View>(), LoginContract.Presenter {

    override fun saveUserLoggedIn() {
        mSpManager.setIsUserLoggedIn(true)
    }

    override fun checkIsUserLoggedIn(): Boolean {
        return mSpManager.isUserLoggedIn
    }
}