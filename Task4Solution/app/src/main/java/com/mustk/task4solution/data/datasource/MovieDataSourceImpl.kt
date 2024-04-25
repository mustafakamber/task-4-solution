package com.mustk.task4solution.data.datasource

import com.mustk.task4solution.data.service.MovieService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(private val movieService: MovieService)  :
    MovieDataSource {
    override fun fetchAllMovieData() = flow {
        emit(movieService.fetchAllMovieData())
    }
    override fun fetchMovieData(imdbId: String?) = flow {
        emit(movieService.fetchMovieData(imdbId))
    }
}