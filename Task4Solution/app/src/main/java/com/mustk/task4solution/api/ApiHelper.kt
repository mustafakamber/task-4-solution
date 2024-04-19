package com.mustk.task4solution.api

import com.mustk.task4solution.model.Movie
import com.mustk.task4solution.model.Search
import com.mustk.task4solution.util.Constant.IMDB_ID_QUERY_PARAM
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Query

interface ApiHelper {
    fun fetchAllMovieData(): Flow<Response<Search>>
    fun fetchMovieData(@Query(IMDB_ID_QUERY_PARAM) imdbId: String?): Flow<Response<Movie>>
}