package com.scally_p.themoviedb.data.model.genres

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Genre : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var name: String? = null
}