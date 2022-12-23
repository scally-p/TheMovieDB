package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.data.model.genres.Genres
import retrofit2.Response

interface IGenresRepository {
    suspend fun fetchGenres(): Response<Genres>
    fun saveGenres(results: List<Genre>)
    fun getGenres(): List<Genre>
}