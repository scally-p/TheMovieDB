package com.scally_p.themoviedb.data.local.db

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException

object RealmConfig {

    fun getConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name("TheMovieDB.realm")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    @Synchronized
    fun getRealm(): Realm {
        return try {
            Realm.getDefaultInstance()
        } catch (e: RealmMigrationNeededException) {
            val config = getConfig()
            Realm.deleteRealm(config)
            Realm.getInstance(config)
        }
    }
}