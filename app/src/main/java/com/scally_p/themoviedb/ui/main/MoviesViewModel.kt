package com.scally_p.themoviedb.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import io.realm.RealmList
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MoviesViewModel : ViewModel(), KoinComponent {

    private val tag: String = MoviesViewModel::class.java.name

    private val moviesRepository by inject<MoviesRepository>()

    private val moviesLiveData = MutableLiveData<List<Result>>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()
    private var page = 1

    private var job: Job? = null

    var currentPage: Int
        get() {
            return page
        }
        set(value) {
            page = value
        }

    val movies: List<Result>
        get() {
            return moviesRepository.getMovies()
        }

    var upcomingMovies: List<Result>
        get() {
            return moviesLiveData.value ?: ArrayList()
        }
        set(upcomingMovies) {
            moviesLiveData.value = upcomingMovies
            Log.d(tag, "setUpcomingMovies - movies size: ${moviesLiveData.value?.size ?: 0}")
        }

    fun getMovieGenresString(genres: RealmList<Int>?): String {
        return moviesRepository.getMovieGenresString(genres)
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            val result = moviesRepository.fetchUpcomingMovies(page)
            if (result.isSuccess) {
                upcomingMovies = moviesRepository.getMovies()
                loading.value = false
            } else {
                result.exceptionOrNull()?.printStackTrace()
                onError(
                    "Message: ${result.exceptionOrNull()?.message}\nLocalizedMessage: ${result.exceptionOrNull()?.localizedMessage}"
                )
            }
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