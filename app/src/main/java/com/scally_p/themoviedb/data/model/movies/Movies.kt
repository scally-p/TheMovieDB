package com.scally_p.themoviedb.data.model.movies

data class Movies(
    val dates: Dates? = null,
    val page: Int? = null,
    val results: List<Result>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)