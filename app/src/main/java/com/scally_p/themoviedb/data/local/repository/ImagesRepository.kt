package com.scally_p.themoviedb.data.local.repository

import com.scally_p.themoviedb.data.api.RetrofitHelper
import com.scally_p.themoviedb.data.network.NetworkHelper
import com.scally_p.themoviedb.data.local.db.ImagesDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IImagesRepository
import com.scally_p.themoviedb.data.model.images.Poster
import com.scally_p.themoviedb.extension.exceptionOrNull
import com.scally_p.themoviedb.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImagesRepository : IImagesRepository, KoinComponent {

    private val imagesDbHelper by inject<ImagesDbHelper>()

    override suspend fun fetchImages(id: Int): Result<Boolean> {
        val apiResponse = NetworkHelper.apiRequest {
            RetrofitHelper.retrofitApiInstance.images(id, Constants.Api.API_KEY)
        }

        return withContext(Dispatchers.IO) {
            val body = apiResponse.getOrNull()?.body()
            if (apiResponse.isSuccess && body != null) {
                saveImages(body.posters, id)
                Result.success(true)
            } else {
                Result.failure(apiResponse.exceptionOrNull()!!)
            }
        }
    }

    override fun saveImages(results: List<Poster>, id: Int) {
        return imagesDbHelper.savePosters(results, id)
    }

    override fun getPosters(id: Int): List<Poster> {
        return imagesDbHelper.getPosters(id)
    }
}