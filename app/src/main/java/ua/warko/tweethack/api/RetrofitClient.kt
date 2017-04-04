package ua.warko.tweethack.api

import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterSession
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import ua.warko.tweethack.App
import ua.warko.tweethack.interfaces.Manager


/**
 * Created by dmitry on 2/6/17.
 */
class RetrofitClient : Manager {


    companion object {
        fun getTwitterService(): TwitterService {

            val logginInterceptor = HttpLoggingInterceptor()
            logginInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val consumer = OkHttpOAuthConsumer(App.TWITTER_KEY, App.TWITTER_SECRET);
            val twitterSession : TwitterSession? = Twitter.getSessionManager().activeSession
            val authToken = twitterSession?.authToken
            consumer.setTokenWithSecret(authToken?.token, authToken?.secret)

            val client = OkHttpClient.Builder()
                    .addInterceptor(logginInterceptor)
                    .addInterceptor(SigningInterceptor(consumer))
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(ApiSettings.SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create<TwitterService>(TwitterService::class.java)
        }
    }

    override fun init() {

    }

    override fun clear() {

    }


}