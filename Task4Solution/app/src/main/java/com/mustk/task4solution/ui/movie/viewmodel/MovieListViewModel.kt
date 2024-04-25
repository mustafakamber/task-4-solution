package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.Movie
import javax.inject.Inject

class MovieListViewModel @Inject constructor(private val movieDataSource: MovieDataSource) :
    BaseViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    fun refreshAllMovieData() {
        fetchMovieListFromAPI()
    }

    private fun fetchMovieListFromAPI() {
        handleNetworkFlow(movieDataSource.fetchAllMovieData()) { searchBody ->
            with(searchBody) {
                handleBody(response, error) {
                    _movieList.value = search
                }
            }
        }
    }
}

