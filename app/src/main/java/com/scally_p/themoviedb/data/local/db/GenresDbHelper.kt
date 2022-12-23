package com.scally_p.themoviedb.data.local.db

import com.scally_p.themoviedb.data.model.genres.Genre

class GenresDbHelper {

    @Synchronized
    fun getGenres(): List<Genre> {
        return RealmConfig.getRealm()
            .use {
                val data = it.where(Genre::class.java).findAll()

                if (data != null && data.isValid) {
                    it.copyFromRealm(data)
                } else {
                    ArrayList()
                }
            }
    }

    @Synchronized
    fun saveGenres(results: List<Genre>) {
        RealmConfig.getRealm()
            .use {
                it.executeTransaction { realm ->
                    realm.copyToRealmOrUpdate(results)
                }
            }
    }

    @Synchronized
    fun deleteGenres(): Boolean {
        return try {
            RealmConfig.getRealm()
                .use {
                    it.executeTransaction { realm ->
                        realm.where(Genre::class.java)
                            .findAll()
                            ?.deleteAllFromRealm()
                    }
                }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}