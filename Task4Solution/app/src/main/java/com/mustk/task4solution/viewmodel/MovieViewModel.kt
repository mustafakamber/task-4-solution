package com.mustk.task4solution.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustk.task4solution.api.ApiHelper
import com.mustk.task4solution.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieViewModel(private val movieHelper: ApiHelper) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movieList

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun refreshAllMovieData() {
        fetchMovieListFromAPI()
    }

    private fun fetchMovieListFromAPI() {
        handleFlow(movieHelper.fetchAllMovieData()) { searchResult ->
            if (searchResult.response) {
                _movieList.value = searchResult.search
            } else {
                searchResult.error?.let {
                    _errorMessage.value = it
                }
            }
        }
    }

    fun fetchMovieDetailFromAPI(imdbId: String) {
        handleFlow(movieHelper.fetchMovieData(imdbId)) { movieResult ->
            if (movieResult.response){
                _movieDetail.value = movieResult
            }else{
                movieResult.error?.let {
                    _errorMessage.value = it
                }
            }
        }
    }

    private suspend fun <T : Any> handleResponse(
        response: Response<T>,
        onSuccess: (T) -> Unit
    ) {
        if (response.isSuccessful) {
            response.body()?.let {
                withContext(Dispatchers.Main) {
                    onSuccess(it)
                }
            }
        }
    }

    private fun <T : Any> handleFlow(
        response: Flow<Response<T>>,
        responseComplete: (T) -> Unit
    ) {
        viewModelScope.launch {
            response.flowOn(Dispatchers.IO).catch { error ->
                _errorMessage.value = error.localizedMessage
            }.collect { apiResponse ->
                if (apiResponse.isSuccessful) {
                    handleResponse(apiResponse) {
                        responseComplete(it)
                    }
                }
            }
        }
    }
}

