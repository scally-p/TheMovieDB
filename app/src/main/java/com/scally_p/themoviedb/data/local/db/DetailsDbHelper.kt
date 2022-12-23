package com.scally_p.themoviedb.data.local.db

import com.scally_p.themoviedb.data.model.details.Details

class DetailsDbHelper {

    @Synchronized
    fun getDetails(id: Int): Details? {
        return RealmConfig.getRealm().use {
                val data = it.where(Details::class.java).equalTo("id", id).findFirst()

                if (data != null && data.isValid) {
                    it.copyFromRealm(data)
                } else {
                    null
                }
            }
    }

    @Synchronized
    fun saveDetails(details: Details) {
        RealmConfig.getRealm().use {
                it.executeTransaction { realm ->
                    realm.copyToRealmOrUpdate(details)
                }
            }
    }

    @Synchronized
    fun deleteDetails(id: Int): Boolean {
        return try {
            RealmConfig.getRealm().use {
                    it.executeTransaction { realm ->
                        realm.where(Details::class.java).equalTo("id", id).findAll()
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