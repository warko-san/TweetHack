package ua.warko.tweethack

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import ua.warko.tweethack.util.CrashlyticsReportingTree


class App : Application() {

    companion object {
        val TWITTER_KEY = "p5Q982bnpEdQ2rrwau4aL64jB"
        val TWITTER_SECRET = "C8fkYUb3C6VM4y4EUnRyJICfzMmJnYV4949SlQdln8CDCUaHa2"
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
            Timber.plant(CrashlyticsReportingTree())
        }

        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, Twitter(authConfig))

        setupRealmDefaultInstance(this)
    }

    private fun setupRealmDefaultInstance(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}
