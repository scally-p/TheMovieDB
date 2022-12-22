package com.scally_p.themoviedb.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Result : RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var adult: Boolean? = null
    var backdrop_path: String? = null
    var genre_ids: RealmList<Int>? = null
    var original_language: String? = null
    var original_title: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var poster_path: String? = null
    var release_date: String? = null
    var title: String? = null
    var video: Boolean? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
}