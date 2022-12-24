package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.data.model.movies.Result
import io.realm.RealmList

interface IMoviesRepository {
    suspend fun fetchUpcomingMovies(page: Int): kotlin.Result<Boolean>
    fun saveUpcomingMovies(results: List<Result>, page: Int)
    fun getMovies(): List<Result>
    fun getMovieGenresString(genres: RealmList<Int>?): String
    fun getMovieGenresList(genres: RealmList<Int>?): RealmList<Genre>
    fun getMovie(id: Int): Result?
}