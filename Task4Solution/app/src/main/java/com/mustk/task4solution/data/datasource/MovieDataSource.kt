package com.mustk.task4solution.data.datasource

import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.data.model.Search
import com.mustk.task4solution.shared.Constant.IMDB_ID_QUERY_PARAM
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Query

interface MovieDataSource {
    fun fetchAllMovieData(): Flow<Response<Search>>
    fun fetchMovieData(@Query(IMDB_ID_QUERY_PARAM) imdbId: String?): Flow<Response<Movie>>
}