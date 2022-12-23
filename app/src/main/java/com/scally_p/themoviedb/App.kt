package com.scally_p.themoviedb

import android.app.Application
import com.scally_p.themoviedb.data.local.db.RealmConfig
import com.scally_p.themoviedb.di.getModules
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfig.getConfig()
        Realm.setDefaultConfiguration(config)

        startKoin {
            androidContext(this@App)
            modules(getModules())
        }
    }
}