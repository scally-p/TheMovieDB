package com.scally_p.themoviedb.data.local

import com.scally_p.themoviedb.data.api.RetrofitInstance
import com.scally_p.themoviedb.data.local.db.GenresDbHelper
import com.scally_p.themoviedb.data.model.Genre
import com.scally_p.themoviedb.data.model.Genres
import com.scally_p.themoviedb.util.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class GenresRepository : IGenresRepository, KoinComponent {

    private val genresDbHelper by inject<GenresDbHelper>()

    override suspend fun fetchGenres(): Response<Genres> {
        return RetrofitInstance.api.genres(Constants.Api.API_KEY)
    }

    override fun saveGenres(results: List<Genre>) {
        return genresDbHelper.saveGenres(results)
    }

    override fun getGenres(): List<Genre> {
        return genresDbHelper.getGenres()
    }
}