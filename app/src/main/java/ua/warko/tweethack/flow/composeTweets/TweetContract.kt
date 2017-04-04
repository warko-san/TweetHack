package ua.warko.tweethack.flow.composeTweets

import com.yalantis.base.BasePresenter
import com.yalantis.base.BaseView

/**
 * Created by dmitry on 1/30/17.
 */
class TweetContract {

    interface Presenter : BasePresenter {

        fun calculateAndSendTweets(text: String)

    }

    interface View : BaseView {

        fun clearUi()

    }
}