package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.movies.Movies
import com.scally_p.themoviedb.data.model.movies.Result
import retrofit2.Response

interface IMoviesRepository {
    suspend fun fetchUpcomingMovies(page: Int): Response<Movies>
    fun saveUpcomingMovies(results: List<Result>, page: Int)
    fun getMovies(): List<Result>
    fun getMovie(id: Int): Result?
}