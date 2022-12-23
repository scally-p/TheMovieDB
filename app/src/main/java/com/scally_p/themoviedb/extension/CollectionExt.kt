package com.scally_p.themoviedb.extension

fun List<Pair<Boolean, String?>>.failure(): Pair<Boolean, String?>? {
    for (t in this) {
        if (!t.first) {
            return t
        }
    }
    return null
}