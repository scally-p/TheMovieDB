package com.scally_p.themoviedb.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent

class MoviesViewModel : ViewModel(), KoinComponent {

    private val tag: String = MoviesViewModel::class.java.name

    private val repository = MoviesRepository()

    private val moviesLiveData = MutableLiveData<List<Result>>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun setUpcomingMovies() {
        moviesLiveData.value = repository.getMovies()
        Log.d(tag, "setUpcomingMovies - movies size: ${moviesLiveData.value?.size ?: 0}")
    }

    fun getUpcomingMovies(page: Int) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.fetchUpcomingMovies(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    repository.saveUpcomingMovies(response.body()?.results ?: ArrayList(), page)
                    setUpcomingMovies()
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
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
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}