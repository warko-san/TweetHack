package com.yalantis

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import ua.warko.tweethack.BuildConfig
import ua.warko.tweethack.CrashlyticsReportingTree



class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
            Timber.plant(CrashlyticsReportingTree())
        }

//        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
//        Fabric.with(this, Twitter(authConfig))

        setupRealmDefaultInstance(this)
    }

    private fun setupRealmDefaultInstance(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}
