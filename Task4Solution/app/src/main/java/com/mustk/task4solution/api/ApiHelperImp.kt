package com.mustk.task4solution.api

import kotlinx.coroutines.flow.flow

class ApiHelperImp(private val movieService : ApiService) : ApiHelper{
    override fun fetchAllMovieData() = flow {
        emit(movieService.fetchAllMovieData())
    }
    override fun fetchMovieData(imdbId: String?) = flow {
        emit(movieService.fetchMovieData(imdbId))
    }
}