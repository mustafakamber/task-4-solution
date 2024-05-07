package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieDataSource) :
    BaseViewModel() {

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie>
        get() = _movieDetail

    fun fetchMovieDetailFromAPI(imdbId: String) {
        safeRequest(
            response = { repository.fetchMovieData(imdbId) },
            successStatusData = { movieData ->
                _movieDetail.value = movieData
            }
        )
    }
}