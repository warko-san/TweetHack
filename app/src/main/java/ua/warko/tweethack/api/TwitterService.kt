package ua.warko.tweethack.api

import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by dmitry on 2/6/17.
 */
interface TwitterService {

    @POST (ApiSettings.UPDATE_STATUS)
    fun updateStatus(@Query(ApiSettings.STATUS) status: String)
}