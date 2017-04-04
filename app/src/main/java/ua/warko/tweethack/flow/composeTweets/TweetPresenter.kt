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
    private var index = 0

    override fun calculateAndSendTweets(text: String) {
        val stringBuilder = StringBuilder(text)
        fillTweetList(stringBuilder)
        tweetList.reverse()
        mView?.showProgress()
        sendTweet(tweetList[index])
    }

    private fun sendTweet(tweet: String) {
        twitter.updateStatus(tweet).enqueue(object : Callback<Tweet>() {

            override fun success(result: Result<Tweet>?) {
                ++index
                if (index < tweetList.size) {
                    sendTweet(tweetList[index])
                } else {
                    mView?.hideProgress()
                    mView?.clearUi()
                }
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(mView?.getContext(), "Failed", Toast.LENGTH_SHORT).show()
                mView?.hideProgress()
            }
        })

    }

    private fun fillTweetList(text: StringBuilder) {
        tweetList.clear()
        val tweetCount: Int = Math.ceil(text.length.toDouble() / 140).toInt()
        var isBeginning = true

        for (j in 1..tweetCount) {
            if (text.length > 140) {
                for (i in 134 downTo 125) {
                    if (text[i] == ' ') {
                        var tweet = text.substring(0, i)
                        if (tweet.length < 134 && !isBeginning) {
                            tweet = "...$tweet..."
                        } else {
                            tweet += "..."
                        }
                        tweetList.add(tweet)
                        text.delete(0, i)
                        isBeginning = false
                        break
                    }
                }

            } else {
                var lastPart = text.toString()
                lastPart = "...$lastPart"
                tweetList.add(lastPart)
            }
        }
    }
}