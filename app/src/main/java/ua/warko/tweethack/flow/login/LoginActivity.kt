package ua.warko.tweethack.flow.login

import android.content.Context
import android.os.Bundle
import com.yalantis.base.BaseActivity
import ua.warko.tweethack.R
import ua.warko.tweethack.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<LoginPresenter, ActivityLoginBinding>(), LoginContract.View {

    override val presenter: LoginPresenter
        get() = LoginPresenter()
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun getContext(): Context {
        return this@LoginActivity
    }

    override fun startTweetActivity() {

    }

}
