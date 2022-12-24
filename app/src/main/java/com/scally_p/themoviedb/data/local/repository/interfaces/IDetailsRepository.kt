package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.details.Details
import com.scally_p.themoviedb.data.model.genres.Genre
import io.realm.RealmList

interface IDetailsRepository {
    suspend fun fetchDetails(id: Int): Result<Boolean>
    fun saveDetails(details: Details)
    fun getDetails(id: Int): Details?
    fun getMovieGenresString(genres: RealmList<Genre>?): String
}