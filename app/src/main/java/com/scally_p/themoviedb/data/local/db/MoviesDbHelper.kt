package com.scally_p.themoviedb.data.local.db

import com.scally_p.themoviedb.data.model.movies.Result

class MoviesDbHelper {

    @Synchronized
    fun getMovies(): List<Result> {
        return RealmConfig.getRealm().use {
                val data = it.where(Result::class.java).findAll()

                if (data != null && data.isValid) {
                    it.copyFromRealm(data)
                } else {
                    ArrayList()
                }
            }
    }

    @Synchronized
    fun getMovie(id: Int): Result? {
        return RealmConfig.getRealm().use {
                val data = it.where(Result::class.java).equalTo("id", id).findFirst()

                if (data != null && data.isValid) {
                    it.copyFromRealm(data)
                } else {
                    null
                }
            }
    }

    @Synchronized
    fun saveMovies(results: List<Result>) {
        RealmConfig.getRealm().use {
                it.executeTransaction { realm ->
                    realm.copyToRealmOrUpdate(results)
                }
            }
    }

    @Synchronized
    fun deleteMovies(): Boolean {
        return try {
            RealmConfig.getRealm().use {
                    it.executeTransaction { realm ->
                        realm.where(Result::class.java).findAll()?.deleteAllFromRealm()
                    }
                }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}