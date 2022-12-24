package com.scally_p.themoviedb.data.local.repository

import android.util.Log
import com.scally_p.themoviedb.data.api.RetrofitHelper
import com.scally_p.themoviedb.data.network.NetworkHelper
import com.scally_p.themoviedb.data.local.db.MoviesDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IMoviesRepository
import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.extension.exceptionOrNull
import com.scally_p.themoviedb.util.Constants
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MoviesRepository : IMoviesRepository, KoinComponent {

    private val tag: String = MoviesRepository::class.java.name

    private val moviesDbHelper by inject<MoviesDbHelper>()

    override suspend fun fetchUpcomingMovies(page: Int): kotlin.Result<Boolean> {
        val apiResponse = NetworkHelper.apiRequest {
            RetrofitHelper.retrofitApiInstance.upcomingMovies(Constants.Api.API_KEY, page)
        }

        return withContext(Dispatchers.IO) {
            val body = apiResponse.getOrNull()?.body()
            if (apiResponse.isSuccess && body != null) {
                saveUpcomingMovies(body.results ?: ArrayList(), page)
                kotlin.Result.success(true)
            } else {
                kotlin.Result.failure(apiResponse.exceptionOrNull()!!)
            }
        }
    }

    override fun saveUpcomingMovies(
        results: List<Result>,
        page: Int
    ) {
        if (page == 1) moviesDbHelper.deleteMovies()
        return moviesDbHelper.saveMovies(results)
    }

    override fun getMovies(): List<Result> {
        return moviesDbHelper.getMovies()
    }

    override fun getMovieGenresString(genres: RealmList<Int>?): String {
        val data = moviesDbHelper.getGenres(genres)
        val s = StringBuilder("")
        data.forEachIndexed { index, genre ->
            Log.d(tag, "Genres - Id: ${genre.id} Name: ${genre.name}")
            Log.d(tag, "Genres -------------------------------")
            if (index == data.lastIndex) s.append("${genre.name}") else s.append("${genre.name} | ")
        }

        return s.toString()
    }

    override fun getMovieGenresList(genres: RealmList<Int>?): RealmList<Genre> {
        val data = moviesDbHelper.getGenres(genres)
        val list: RealmList<Genre> = RealmList()
        list.addAll(data)
        return list
    }

    override fun getMovie(id: Int): Result? {
        return moviesDbHelper.getMovie(id)
    }
}