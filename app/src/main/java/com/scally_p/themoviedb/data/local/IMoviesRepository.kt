package com.scally_p.themoviedb.data.local

import com.scally_p.themoviedb.data.model.Movies
import retrofit2.Response

interface IMoviesRepository {
    suspend fun fetchUpcomingMovies(page: Int): Response<Movies>
    fun saveUpcomingMovies(results: List<com.scally_p.themoviedb.data.model.Result>, page: Int)
    fun getMovies(): List<com.scally_p.themoviedb.data.model.Result>
}