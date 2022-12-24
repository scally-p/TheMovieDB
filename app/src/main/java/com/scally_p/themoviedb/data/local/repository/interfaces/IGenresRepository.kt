package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.data.model.genres.Genres

interface IGenresRepository {
    suspend fun fetchGenres(): Result<Boolean>
    fun saveGenres(genres: Genres?)
    fun getGenres(): List<Genre>
}