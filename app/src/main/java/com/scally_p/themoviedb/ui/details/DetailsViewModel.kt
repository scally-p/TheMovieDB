package com.scally_p.themoviedb.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scally_p.themoviedb.data.local.repository.DetailsRepository
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import com.scally_p.themoviedb.data.model.details.Details
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent

class DetailsViewModel : ViewModel(), KoinComponent {

    private val tag: String = DetailsViewModel::class.java.name

    private val moviesRepository = MoviesRepository()
    private val detailsRepository = DetailsRepository()

    private val detailsLiveData = MutableLiveData<Details>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var id: Int? = null
    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

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
//            d.genres = result.id
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

    fun getDetails() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = detailsRepository.fetchDetails(id ?: 0)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    detailsRepository.saveDetails(response.body() ?: Details())
                    setDetails()
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun observeDetailsLiveData(): MutableLiveData<Details> {
        return detailsLiveData
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