package ua.warko.tweethack.api

import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterSession

/**
 * Created by dmitry on 3/6/17.
 */
class TwitterClient (session: TwitterSession) : TwitterApiClient(session) {

    fun getStatusService(): TwitterService {
        return getService(TwitterService::class.java)
    }

}