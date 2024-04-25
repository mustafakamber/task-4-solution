package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.Movie
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val movieDataSource: MovieDataSource) :
    BaseViewModel() {

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie>
        get() = _movieDetail

    fun fetchMovieDetailFromAPI(imdbId: String) {
        handleNetworkFlow(movieDataSource.fetchMovieData(imdbId)) { movieBody ->
            with(movieBody) {
                handleBody(response, error) {
                    _movieDetail.value = this
                }
            }
        }
    }
}