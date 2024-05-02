package com.mustk.task4solution.data.datasource

import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.shared.Constant.IMDB_ID_QUERY_PARAM
import com.mustk.task4solution.shared.Constant.TITLE_QUERY_PARAM
import com.mustk.task4solution.shared.Constant.TYPE_QUERY_PARAM
import com.mustk.task4solution.shared.Resource
import retrofit2.http.Query

interface MovieDataSource {
    suspend fun fetchAllMovieData(): Resource<List<Movie>>

    suspend fun fetchMovieData(@Query(IMDB_ID_QUERY_PARAM) imdbId: String): Resource<Movie>
    
    suspend fun searchMovieData(
        @Query(TITLE_QUERY_PARAM) title: String,
        @Query(TYPE_QUERY_PARAM) type: String
    ): Resource<List<Movie>>
}