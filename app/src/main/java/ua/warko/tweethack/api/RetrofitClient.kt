package ua.warko.tweethack.api

import android.util.Base64
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterSession
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ua.warko.tweethack.App
import ua.warko.tweethack.interfaces.Manager
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * Created by dmitry on 2/6/17.
 */
class RetrofitClient : Manager {


    companion object {
        fun getTwitterService(): TwitterService {
            val logginInterceptor = HttpLoggingInterceptor()
            logginInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val twitterSession : TwitterSession? = Twitter.getSessionManager().activeSession
            val authToken = twitterSession?.authToken

            val client = OkHttpClient.Builder()
                    .addInterceptor(logginInterceptor)
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val b = ByteArray(32)
                        Random().nextBytes(b)
                        var randomBytes: String? = null
                        try {
                            randomBytes = BigInteger(130, SecureRandom()).toString(32)
                        } catch (e: UnsupportedEncodingException) {

                        }


                        val currentTimeMillis = System.currentTimeMillis()
                        val token = twitterSession?.getAuthToken()?.token
                        val signature = String.format("oauth_consumer_key=%s&oauth_nonce=%s&oauth" +
                                "_signature_method=HMAC-SHA1&oauth_timestamp=%s&oauth_token=%s&oauth_version=1.0",
                                App.TWITTER_KEY, randomBytes, currentTimeMillis, token)
                        var finalSignature: String? = null
                        try {
                            finalSignature = sha1(signature, App.TWITTER_SECRET + "&" + authToken?.secret)
                        } catch (e: UnsupportedEncodingException) {

                        } catch (e: NoSuchAlgorithmException) {

                        } catch (e: InvalidKeyException) {

                        }


                        val header = String.format("OAuth oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"%s\", oauth_token=\"%s\", oauth_version=\"1.0\"",
                                App.TWITTER_KEY, randomBytes, finalSignature?.trim(), currentTimeMillis, token)
                        val newRequest = originalRequest.newBuilder()
                                .method(originalRequest.method(), originalRequest.body())
                                .header("Authorization", header)
                                .build()
                        chain.proceed(newRequest)
                    }
                    .build()


            val retrofit = Retrofit.Builder()
                    .baseUrl(ApiSettings.SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create<TwitterService>(TwitterService::class.java)
        }

        @Throws(UnsupportedEncodingException::class, NoSuchAlgorithmException::class, InvalidKeyException::class)
        fun sha1(s: String, keyString: String): String {

            val key = SecretKeySpec(keyString.toByteArray(charset("UTF-8")), "HmacSHA1")
            val mac = Mac.getInstance("HmacSHA1")
            mac.init(key)
            val bytes = mac.doFinal(s.toByteArray(charset("UTF-8")))

            return Base64.encodeToString(bytes, Base64.URL_SAFE)
        }
    }




    override fun init() {

    }


    override fun clear() {

    }


}