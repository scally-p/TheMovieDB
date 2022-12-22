package com.scally_p.themoviedb.data.api

import com.scally_p.themoviedb.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api : Api by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.Urls.SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}