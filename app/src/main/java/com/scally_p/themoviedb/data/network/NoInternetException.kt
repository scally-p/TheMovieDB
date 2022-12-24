package com.scally_p.themoviedb.data.network

import java.io.IOException

class NoInternetException: IOException("No Internet Connection") {
    override fun toString(): String {
        return "No Internet Connection"
    }
}