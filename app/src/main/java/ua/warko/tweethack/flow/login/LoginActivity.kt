package ua.warko.tweethack.flow.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.yalantis.base.BaseActivity
import ua.warko.tweethack.R
import ua.warko.tweethack.databinding.ActivityLoginBinding
import ua.warko.tweethack.flow.composeTweets.TweetActivity


class LoginActivity : BaseActivity<LoginPresenter, ActivityLoginBinding>(), LoginContract.View {

    override val presenter: LoginPresenter = LoginPresenter()
    override val layoutResourceId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: move this shit to splash later
        if (presenter.checkIsUserLoggedIn()) {
            startActivity(TweetActivity.newIntent(this@LoginActivity))
        }

        initLogin()
    }

    private fun initLogin() {
        binding.btnLogin.callback = (object : Callback<TwitterSession>() {

            override fun success(result: Result<TwitterSession>) {
                presenter.saveUserLoggedIn()
                Twitter.getInstance().core.sessionManager.activeSession
                startActivity(TweetActivity.newIntent(this@LoginActivity))
            }

            override fun failure(exception: TwitterException) {
                // Do something on failure
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.btnLogin.onActivityResult(requestCode, resultCode, data)
    }

    override fun getContext(): Context {
        return this@LoginActivity
    }

}
