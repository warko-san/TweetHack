package ua.warko.tweethack.api

import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by dmitry on 2/6/17.
 */
interface TwitterService {

    @FormUrlEncoded
    @POST (ApiSettings.UPDATE_STATUS)
    fun updateStatus(@Field("status") status: String) : Call<Tweet>
}