package com.scally_p.themoviedb.data.local.repository

import com.scally_p.themoviedb.data.api.RetrofitInstance
import com.scally_p.themoviedb.data.local.db.MoviesDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IMoviesRepository
import com.scally_p.themoviedb.data.model.movies.Movies
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.util.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class MoviesRepository : IMoviesRepository, KoinComponent {

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

    override fun getMovie(id: Int): Result? {
        return moviesDbHelper.getMovie(id)
    }
}