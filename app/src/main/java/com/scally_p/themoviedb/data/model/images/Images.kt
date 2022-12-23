package com.scally_p.themoviedb.data.model.images

data class Images(
    val backdrops: List<Backdrop>,
    val id: Int,
    val logos: List<Logo>,
    val posters: List<Poster>
)