package com.scally_p.themoviedb.data.local.repository

import android.util.Log
import com.scally_p.themoviedb.data.api.RetrofitInstance
import com.scally_p.themoviedb.data.local.db.MoviesDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IMoviesRepository
import com.scally_p.themoviedb.data.model.movies.Movies
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.util.Constants
import io.realm.RealmList
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class MoviesRepository : IMoviesRepository, KoinComponent {

    private val tag: String = MoviesRepository::class.java.name

    private val moviesDbHelper by inject<MoviesDbHelper>()

    override suspend fun fetchUpcomingMovies(page: Int): Response<Movies> {
        return RetrofitInstance.api.upcomingMovies(Constants.Api.API_KEY, page)
    }

    override fun saveUpcomingMovies(
        results: List<Result>,
        page: Int
    ) {
        if (page > 1) moviesDbHelper.deleteMovies()
        return moviesDbHelper.saveMovies(results)
    }

    override fun getMovies(): List<Result> {
        return moviesDbHelper.getMovies()
    }

    override fun getMovieGenres(genres: RealmList<Int>?): String {
        val data = moviesDbHelper.getGenres(genres)
        val s = StringBuilder("")
        data.forEachIndexed { index, genre ->
            Log.d(tag, "Genres - Id: ${genre.id} Name: ${genre.name}")
            Log.d(tag, "Genres -------------------------------")
            if (index == data.lastIndex) s.append("${genre.name}") else s.append("${genre.name} | ")
        }

        return s.toString()
    }

    override fun getMovie(id: Int): Result? {
        return moviesDbHelper.getMovie(id)
    }
}