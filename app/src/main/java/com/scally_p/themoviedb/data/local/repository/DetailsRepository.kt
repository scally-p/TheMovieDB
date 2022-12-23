package com.scally_p.themoviedb.data.local.repository

import com.scally_p.themoviedb.data.api.RetrofitInstance
import com.scally_p.themoviedb.data.local.db.DetailsDbHelper
import com.scally_p.themoviedb.data.local.repository.interfaces.IDetailsRepository
import com.scally_p.themoviedb.data.model.details.Details
import com.scally_p.themoviedb.util.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class DetailsRepository : IDetailsRepository, KoinComponent {

    private val detailsDbHelper by inject<DetailsDbHelper>()

    override suspend fun fetchDetails(id: Int): Response<Details> {
        return RetrofitInstance.api.details(id, Constants.Api.API_KEY)
    }

    override fun saveDetails(details: Details) {
        detailsDbHelper.saveDetails(details)
    }

    override fun getDetails(id: Int): Details? {
        return detailsDbHelper.getDetails(id)
    }
}