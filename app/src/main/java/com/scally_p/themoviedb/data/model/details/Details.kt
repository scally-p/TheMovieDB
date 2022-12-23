package com.scally_p.themoviedb.data.model.details

import com.scally_p.themoviedb.data.model.genres.Genre
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Details : RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var adult: Boolean? = null
    var backdrop_path: String? = null
    var budget: Int? = null
    var genres: RealmList<Genre>? = null
    var homepage: String? = null
    var imdb_id: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var poster_path: String? = null
    var release_date: String? = null
    var revenue: Int? = null
    var runtime: Int? = null
    var status: String? = null
    var tagline: String? = null
    var title: String? = null
    var video: Boolean? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
}