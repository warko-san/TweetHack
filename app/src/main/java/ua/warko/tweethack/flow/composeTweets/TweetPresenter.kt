package ua.warko.tweethack.flow.composeTweets

import com.yalantis.base.BasePresenterImplementation
import java.util.*

/**
 * Created by dmitry on 1/30/17.
 */
class TweetPresenter : BasePresenterImplementation<TweetContract.View>(), TweetContract.Presenter {

    override fun calculateAndSendTweets(text: String) {
        var tweetList = ArrayList<String>()
        var stringBuilder = StringBuilder(text)
        while (stringBuilder.isNotEmpty()) {
            val tweet = calculateTweets(stringBuilder)
            tweetList.add(tweet)
            stringBuilder.delete(0, tweet.length)
        }
    }

    private fun calculateTweets(text: StringBuilder) : String {
        for (i in 139..120) {
            if (text[i].equals(" "))
                text.substring(0, i)
        }
        return text.toString()
    }
}