package ua.warko.tweethack.flow.composeTweets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yalantis.base.BaseActivity
import ua.warko.tweethack.R
import ua.warko.tweethack.databinding.ActivityTweetBinding

class TweetActivity : BaseActivity<TweetPresenter, ActivityTweetBinding>(), TweetContract.View {

    override val presenter: TweetPresenter = TweetPresenter()
    override val layoutResourceId: Int = R.layout.activity_tweet

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TweetActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnTweet.setOnClickListener { presenter.calculateAndSendTweets(binding.etTweet.text.toString()) }
    }

    override fun getContext(): Context {
        return this@TweetActivity
    }
}
