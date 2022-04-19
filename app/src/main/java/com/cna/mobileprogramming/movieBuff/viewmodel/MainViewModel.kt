package com.cna.mobileprogramming.movieBuff.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cna.mobileprogramming.movieBuff.model.Movie
import com.cna.mobileprogramming.movieBuff.repository.MainRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    val ratedList = MutableLiveData<List<Movie>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }


    fun getHighRatedMovies() {
        /*job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getHighRatedMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    for (item in response.body()!!){
                        if (item.category == "High Rated"){
                        ratedList.add(item)
            }
        }
        ratedList.postValue(list)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }*/


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