package com.scally_p.themoviedb.data.api

import com.scally_p.themoviedb.data.model.details.Details
import com.scally_p.themoviedb.data.model.genres.Genres
import com.scally_p.themoviedb.data.model.movies.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/upcoming")
    suspend fun upcomingMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("genre/movie/list")
    suspend fun genres(@Query("api_key") api_key: String): Response<Genres>

    @GET("movie/{id}")
    suspend fun details(@Path("id") id: Int, @Query("api_key") api_key: String): Response<Details>
}