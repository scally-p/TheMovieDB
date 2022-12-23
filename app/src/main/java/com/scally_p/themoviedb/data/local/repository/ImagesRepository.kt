package com.scally_p.themoviedb.data.local.repository

import com.scally_p.themoviedb.data.api.RetrofitInstance
import com.scally_p.themoviedb.data.local.db.ImagesDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IImagesRepository
import com.scally_p.themoviedb.data.model.images.Images
import com.scally_p.themoviedb.data.model.images.Poster
import com.scally_p.themoviedb.util.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class ImagesRepository : IImagesRepository, KoinComponent {

    private val imagesDbHelper by inject<ImagesDbHelper>()

    override suspend fun fetchImages(id: Int): Response<Images> {
        return RetrofitInstance.api.images(id, Constants.Api.API_KEY)
    }

    override fun saveImages(results: List<Poster>, id: Int) {
        return imagesDbHelper.savePosters(results, id)
    }

    override fun getPosters(id: Int): List<Poster> {
        return imagesDbHelper.getPosters(id)
    }
}