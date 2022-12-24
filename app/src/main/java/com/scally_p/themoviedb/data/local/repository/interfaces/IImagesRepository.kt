package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.images.Poster

interface IImagesRepository {
    suspend fun fetchImages(id: Int): Result<Boolean>
    fun saveImages(results: List<Poster>, id: Int)
    fun getPosters(id: Int): List<Poster>
}