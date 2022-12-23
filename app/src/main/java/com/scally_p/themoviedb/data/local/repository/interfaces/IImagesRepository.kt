package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.images.Images
import com.scally_p.themoviedb.data.model.images.Poster
import retrofit2.Response

interface IImagesRepository {
    suspend fun fetchImages(id: Int): Response<Images>
    fun saveImages(results: List<Poster>, id: Int)
    fun getPosters(id: Int): List<Poster>
}