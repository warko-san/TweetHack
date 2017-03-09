package ua.warko.tweethack.flow.composeTweets

import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.yalantis.base.BasePresenterImplementation
import java.util.*

/**
 * Created by dmitry on 1/30/17.
 */
class TweetPresenter : BasePresenterImplementation<TweetContract.View>(), TweetContract.Presenter {

    private var tweetList = ArrayList<String>()

    override fun calculateAndSendTweets(text: String) {
        val stringBuilder = StringBuilder(text)
        fillTweetList(stringBuilder)
        tweetList.reverse()
        tweetList.forEach { sendTweet(it) }
    }

    private fun sendTweet(tweet: String) {
        twitter.updateStatus(tweet).enqueue(object : Callback<Tweet>() {
            override fun success(result: Result<Tweet>?) {
                Toast.makeText(mView?.getContext(), "Tweeted", Toast.LENGTH_SHORT).show()
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(mView?.getContext(), "Failed", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun fillTweetList(text: StringBuilder) {
        val tweetCount: Int = Math.ceil(text.length.toDouble() / 140).toInt()

        for (j in 1..tweetCount) {
            if (text.length > 125) {
                for (i in 140 downTo 125) {
                    if (text[i] == ' ') {
                        val tweet = text.substring(0, i)
                        tweetList.add(tweet)
                        text.delete(0, i)
                        break
                    }
                }

            } else {
                tweetList.add(text.toString())
            }
        }
    }
}