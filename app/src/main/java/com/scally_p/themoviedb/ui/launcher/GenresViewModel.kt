package com.scally_p.themoviedb.ui.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scally_p.themoviedb.data.local.repository.GenresRepository
import com.scally_p.themoviedb.data.model.genres.Genre
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenresViewModel : ViewModel(), KoinComponent {

    private val genresRepository by inject<GenresRepository>()

    private var mGenres: List<Genre> = ArrayList()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var job: Job? = null

    var genres: List<Genre>
        get() {
            mGenres = genresRepository.getGenres()
            return mGenres
        }
        set(genres) {
            mGenres = genres
        }

    fun fetchGenres() {
        viewModelScope.launch {
            val result = genresRepository.fetchGenres()
            if (result.isSuccess) {
                loading.value = false
            } else {
                result.exceptionOrNull()?.printStackTrace()
                onError(
                    "Message: ${result.exceptionOrNull()?.message}\nLocalizedMessage: ${result.exceptionOrNull()?.localizedMessage}"
                )
            }
        }
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