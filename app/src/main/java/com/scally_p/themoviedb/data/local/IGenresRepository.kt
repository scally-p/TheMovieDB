package com.scally_p.themoviedb.data.local

import com.scally_p.themoviedb.data.model.Genre
import com.scally_p.themoviedb.data.model.Genres
import retrofit2.Response

interface IGenresRepository {
    suspend fun fetchGenres(): Response<Genres>
    fun saveGenres(results: List<Genre>)
    fun getGenres(): List<Genre>
}