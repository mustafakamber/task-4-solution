package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.shared.Constant.JOKER_KEY
import com.mustk.task4solution.shared.Constant.TYPE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieDataSource) :
    BaseViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    private var job: Job? = null

    fun refreshAllMovieData() {
        fetchMovieListFromAPI()
    }

    fun searchMovieListFromAPI(movieTitle: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            if (movieTitle.isNotEmpty()) {
                val queryTitle = movieTitle.lowercase().trim() + JOKER_KEY
                safeResponse(
                    block = { repository.searchMovieData(queryTitle, TYPE_KEY) },
                    successStatusData = { searchedMovies ->
                        _movieList.value = searchedMovies
                    }
                )
            } else {
                fetchMovieListFromAPI()
            }
        }
    }

    private fun fetchMovieListFromAPI() {
        safeResponse(
            block = { repository.fetchAllMovieData() },
            successStatusData = { movieListData ->
                _movieList.value = movieListData
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}