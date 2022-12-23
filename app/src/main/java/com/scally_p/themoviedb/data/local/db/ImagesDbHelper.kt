package com.scally_p.themoviedb.data.local.db

import com.scally_p.themoviedb.data.model.images.Poster

class ImagesDbHelper {

    @Synchronized
    fun getPosters(detailId: Int): List<Poster> {
        return RealmConfig.getRealm()
            .use {
                val data = it.where(Poster::class.java)
                    .equalTo("detailId", detailId)
                    .findAll()

                if (data != null && data.isValid) {
                    it.copyFromRealm(data)
                } else {
                    ArrayList()
                }
            }
    }

    @Synchronized
    fun savePosters(posters: List<Poster>, detailId: Int) {
        if (posters.isNotEmpty()) {
            posters.onEach { poster ->
                poster.detailId = detailId
            }
        }

        deletePosters(detailId)

        RealmConfig.getRealm()
            .use {
                it.executeTransaction { realm ->
                    realm.copyToRealmOrUpdate(posters)
                }
            }
    }

    @Synchronized
    fun deletePosters(detailId: Int): Boolean {
        return try {
            RealmConfig.getRealm()
                .use {
                    it.executeTransaction { realm ->
                        realm.where(Poster::class.java)
                            .equalTo("detailId", detailId)
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