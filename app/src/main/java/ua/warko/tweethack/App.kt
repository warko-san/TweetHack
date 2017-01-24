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



class App : Application() {

    private val TWITTER_KEY = "Mnz427uXkgAFNsQAzDamaT7jJ"
    private val TWITTER_SECRET = "yFi6HSrOJTQLOGo72sA8mx2DsEmpXt0O1T9t9wv3CXWkaK7n1Y"

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
