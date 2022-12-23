package com.scally_p.themoviedb.extension

fun List<Boolean>.hasFailure(): Boolean {
    for (t in this) {
        if (!t) {
            return false
        }
    }
    return true
}