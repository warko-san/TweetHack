package ua.warko.tweethack.flow.composeTweets

import com.yalantis.base.BasePresenterImplementation
import java.util.*

/**
 * Created by dmitry on 1/30/17.
 */
class TweetPresenter : BasePresenterImplementation<TweetContract.View>(), TweetContract.Presenter {

    private var tweetList = ArrayList<String>()

    override fun calculateAndSendTweets(text: String) {
        val stringBuilder = StringBuilder(text)
        calculateTweets(stringBuilder)

    }

    private fun calculateTweets(text: StringBuilder) {
        if (text.length > 140) {
            for (i in 139..120) {
                if (text[i].equals(" ")) {
                    var newText: StringBuilder = StringBuilder()
                    val tweet = text.substring(0, i)
                    tweetList.add(tweet)
                    text.delete(0, i)
                    newText = text
                }
            }
            calculateTweets(newText)
        } else {
            tweetList.add(text.toString())
        }
    }
}