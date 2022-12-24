package com.scally_p.themoviedb.data.local.repository

import com.scally_p.themoviedb.data.api.RetrofitHelper
import com.scally_p.themoviedb.data.network.NetworkHelper
import com.scally_p.themoviedb.data.local.db.GenresDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IGenresRepository
import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.data.model.genres.Genres
import com.scally_p.themoviedb.extension.exceptionOrNull
import com.scally_p.themoviedb.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenresRepository : IGenresRepository, KoinComponent {

    private val genresDbHelper by inject<GenresDbHelper>()

    override suspend fun fetchGenres(): Result<Boolean> {
        val apiResponse = NetworkHelper.apiRequest {
            RetrofitHelper.retrofitApiInstance.genres(Constants.Api.API_KEY)
        }

        return withContext(Dispatchers.IO) {
            val body = apiResponse.getOrNull()?.body()
            if (apiResponse.isSuccess && body!= null) {
                saveGenres(body)
                Result.success(true)
            } else {
                Result.failure(apiResponse.exceptionOrNull()!!)
            }
        }
    }

    override fun saveGenres(genres: Genres?) {
        return genresDbHelper.saveGenres(genres?.genres ?: ArrayList())
    }

    override fun getGenres(): List<Genre> {
        return genresDbHelper.getGenres()
    }
}