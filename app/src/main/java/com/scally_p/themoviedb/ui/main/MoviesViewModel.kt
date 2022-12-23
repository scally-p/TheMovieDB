package com.scally_p.themoviedb.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scally_p.themoviedb.data.local.repository.GenresRepository
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import com.scally_p.themoviedb.data.model.genres.Genre
import com.scally_p.themoviedb.extension.hasFailure
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent

class MoviesViewModel : ViewModel(), KoinComponent {

    private val tag: String = MoviesViewModel::class.java.name

    private val genresRepository = GenresRepository()
    private val moviesRepository = MoviesRepository()

    private val genresLiveData = MutableLiveData<List<Genre>>()
    private val moviesLiveData = MutableLiveData<List<Result>>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun setUpcomingMovies() {
        genresLiveData.value = genresRepository.getGenres()
        Log.d(tag, "setUpcomingMovies - genres size: ${genresLiveData.value?.size ?: 0}")

        moviesLiveData.value = moviesRepository.getMovies()
        Log.d(tag, "setUpcomingMovies - movies size: ${moviesLiveData.value?.size ?: 0}")
    }

    suspend fun getUpcomingMovies(page: Int) {
        coroutineScope {
            val v = listOf(
                async(Dispatchers.IO + exceptionHandler) { fetchGenres() },
                async(Dispatchers.IO + exceptionHandler) { fetchMovies(page) },
            ).awaitAll()


            if (v.hasFailure()) {
                onError("Error")

            } else {
                setUpcomingMovies()
                loading.value = false
            }
        }
    }

    private suspend fun fetchGenres(): Boolean {
        val response = genresRepository.fetchGenres()
        return if (response.isSuccessful) {
            genresRepository.saveGenres(response.body()?.genres ?: ArrayList())
            true
        } else {
            onError("Error : ${response.message()} ")
            false
        }
    }

    private suspend fun fetchMovies(page: Int): Boolean {
        val response = moviesRepository.fetchUpcomingMovies(page)
        return if (response.isSuccessful) {
            moviesRepository.saveUpcomingMovies(response.body()?.results ?: ArrayList(), page)
            true
        } else {
            onError("Error : ${response.message()} ")
            false
        }
    }

    fun observeMoviesLiveData(): MutableLiveData<List<Result>> {
        return moviesLiveData
    }

    fun observeErrorMessage(): MutableLiveData<String> {
        return errorMessage
    }

    fun observeLoading(): MutableLiveData<Boolean> {
        return loading
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}