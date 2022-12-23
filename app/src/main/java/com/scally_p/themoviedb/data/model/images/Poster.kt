package com.scally_p.themoviedb.data.model.images

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Poster: RealmObject() {

    @PrimaryKey
    var file_path: String? = null
    var detailId: Int? = null
    var aspect_ratio: Double? = null
    var height: Int? = null
    var iso_639_1: String? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
    var width: Int? = null
}