package com.scally_p.themoviedb

import android.app.Application
import com.scally_p.themoviedb.di.getModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(getModules())
        }
    }
}