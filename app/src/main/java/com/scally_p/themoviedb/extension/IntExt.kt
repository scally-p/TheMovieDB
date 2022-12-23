package com.scally_p.themoviedb.extension

fun Int?.toDuration(): String {
    if (this == null) return ""

    val divisor = 60

    val quotient = this / divisor
    val remainder = this % divisor

    return "${quotient}h ${remainder}min"
}