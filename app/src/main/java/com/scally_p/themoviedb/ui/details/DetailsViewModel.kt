package com.scally_p.themoviedb.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scally_p.themoviedb.data.local.repository.DetailsRepository
import com.scally_p.themoviedb.data.local.repository.ImagesRepository
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import com.scally_p.themoviedb.data.model.details.Details
import com.scally_p.themoviedb.data.model.images.Poster
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailsViewModel : ViewModel(), KoinComponent {

    private val tag: String = DetailsViewModel::class.java.name

    private val moviesRepository by inject<MoviesRepository>()
    private val detailsRepository by inject<DetailsRepository>()
    private val imagesRepository by inject<ImagesRepository>()

    private val detailsLiveData = MutableLiveData<Details>()
    private val postersLiveData = MutableLiveData<List<Poster>>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var id: Int? = null
    private var job: Job? = null

    fun setId(id: Int) {
        this.id = id
        Log.d(tag, "setId - id: $id")
    }

    fun setDetails() {
        var details = detailsRepository.getDetails(id ?: 0)
        if (details == null) {
            val result = moviesRepository.getMovie(id ?: 0)
            result?.let {
                val d = Details()
                d.id = result.id
                d.backdrop_path = result.backdrop_path
                d.genres = moviesRepository.getMovieGenresList(result.genre_ids)
                d.poster_path = result.poster_path
                d.release_date = result.release_date
                d.title = result.title
                d.vote_average = result.vote_average
                d.vote_count = result.vote_count

                details = d
            }
        }

        detailsLiveData.value = details!!
    }

    private fun setPosters() {
        postersLiveData.value = imagesRepository.getPosters(id ?: 0)
    }

    val details: Details?
        get() {
            return detailsLiveData.value
        }

    val movieGenres: String
        get() {
            return detailsRepository.getMovieGenresString(details?.genres)
        }

    fun fetchDetails() {
        viewModelScope.launch {
            val result = detailsRepository.fetchDetails(id ?: 0)
            if (result.isSuccess) {
                setDetails()
                loading.value = false
            } else {
                result.exceptionOrNull()?.printStackTrace()
                onError(
                    "Message: ${result.exceptionOrNull()?.message}\nLocalizedMessage: ${result.exceptionOrNull()?.localizedMessage}"
                )
            }
        }
    }

    fun fetchImages() {
        viewModelScope.launch {
            val result = imagesRepository.fetchImages(id ?: 0)
            if (result.isSuccess) {
                setPosters()
            } else {
                result.exceptionOrNull()?.printStackTrace()
                onError(
                    "Message: ${result.exceptionOrNull()?.message}\nLocalizedMessage: ${result.exceptionOrNull()?.localizedMessage}"
                )
            }
        }
    }

    fun observeDetailsLiveData(): MutableLiveData<Details> {
        return detailsLiveData
    }

    fun observePostersLiveData(): MutableLiveData<List<Poster>> {
        return postersLiveData
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