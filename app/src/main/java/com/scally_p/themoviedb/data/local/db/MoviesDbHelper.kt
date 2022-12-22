package com.scally_p.themoviedb.data.local.db

import io.realm.Case

class MoviesDbHelper {

    @Synchronized
    fun getMovies(): List<com.scally_p.themoviedb.data.model.Result> {
        return RealmConfig.getRealm()
            .use {
                val data = it.where(com.scally_p.themoviedb.data.model.Result::class.java).findAll()

                if (data != null && data.isValid) {
                    it.copyFromRealm(data)
                } else {
                    ArrayList()
                }
            }
    }

    @Synchronized
    fun saveMovies(results: List<com.scally_p.themoviedb.data.model.Result>) {
        RealmConfig.getRealm()
            .use {
                it.executeTransaction { realm ->
                    realm.copyToRealmOrUpdate(results)
                }
            }
    }

    @Synchronized
    fun deleteMovies(): Boolean {
        return try {
            RealmConfig.getRealm()
                .use {
                    it.executeTransaction { realm ->
                        realm.where(com.scally_p.themoviedb.data.model.Result::class.java)
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