package com.scally_p.themoviedb.data.api

import com.scally_p.themoviedb.data.model.Movies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/upcoming")
    suspend fun upcomingMovies(@Query("api_key") api_key: String, @Query("page") page: Int): Response<Movies>
}