package com.scally_p.themoviedb.extension

fun Double?.get5StarRating(): Double {
    if (this == null) return 0.0
    return (5.0 / 10.0) * this
}