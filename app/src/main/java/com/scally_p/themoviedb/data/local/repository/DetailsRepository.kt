package com.scally_p.themoviedb.data.local.repository

import android.util.Log
import com.scally_p.themoviedb.data.api.RetrofitHelper
import com.scally_p.themoviedb.data.network.NetworkHelper
import com.scally_p.themoviedb.data.local.db.DetailsDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IDetailsRepository
import com.scally_p.themoviedb.data.model.details.Details
import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.extension.exceptionOrNull
import com.scally_p.themoviedb.util.Constants
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailsRepository : IDetailsRepository, KoinComponent {

    private val tag: String = DetailsRepository::class.java.name

    private val detailsDbHelper by inject<DetailsDbHelper>()

    override suspend fun fetchDetails(id: Int): Result<Boolean> {
        val apiResponse = NetworkHelper.apiRequest {
            RetrofitHelper.retrofitApiInstance.details(id, Constants.Api.API_KEY)
        }

        return withContext(Dispatchers.IO) {
            val body = apiResponse.getOrNull()?.body()
            if (apiResponse.isSuccess && body != null) {
                saveDetails(body)
                Result.success(true)
            } else {
                Result.failure(apiResponse.exceptionOrNull()!!)
            }
        }
    }

    override fun saveDetails(details: Details) {
        detailsDbHelper.saveDetails(details)
    }

    override fun getDetails(id: Int): Details? {
        return detailsDbHelper.getDetails(id)
    }

    override fun getMovieGenresString(genres: RealmList<Genre>?): String {
        val s = StringBuilder("")
        genres?.forEachIndexed { index, genre ->
            Log.d(tag, "Genres - Id: ${genre.id} Name: ${genre.name}")
            Log.d(tag, "Genres -------------------------------")
            if (index == genres.lastIndex) s.append("${genre.name}") else s.append("${genre.name} | ")
        }

        return s.toString()
    }

}