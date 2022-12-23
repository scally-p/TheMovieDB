package com.scally_p.themoviedb.data.local.repository.interfaces

import com.scally_p.themoviedb.data.model.details.Details
import retrofit2.Response

interface IDetailsRepository {
    suspend fun fetchDetails(id: Int): Response<Details>
    fun saveDetails(details: Details)
    fun getDetails(id: Int): Details?
}