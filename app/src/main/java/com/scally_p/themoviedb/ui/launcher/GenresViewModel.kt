package com.scally_p.themoviedb.ui.launcher

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scally_p.themoviedb.data.local.repository.GenresRepository
import com.scally_p.themoviedb.data.model.genres.Genre
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent

class GenresViewModel : ViewModel(), KoinComponent {

    private val tag: String = GenresViewModel::class.java.name

    private val genresRepository = GenresRepository()

    private val genresLiveData = MutableLiveData<List<Genre>>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun setGenres() {
        genresLiveData.value = genresRepository.getGenres()
    }

    fun getGenres(): List<Genre> {
        setGenres()
        return genresLiveData.value ?: ArrayList()
    }

    fun fetchGenres() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = genresRepository.fetchGenres()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    genresRepository.saveGenres(response.body()?.genres ?: ArrayList())
//                    setGenres()
                    loading.value = false
                } else {
                    Log.d(tag, "Error : ${response.message()} ")
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun observeGenresLiveData(): MutableLiveData<List<Genre>> {
        return genresLiveData
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