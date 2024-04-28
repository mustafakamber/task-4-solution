package com.mustk.task4solution.data.service

import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.data.model.Search
import com.mustk.task4solution.shared.Constant.ALL_MOVIE_EXT_URL
import com.mustk.task4solution.shared.Constant.IMDB_ID_QUERY_PARAM
import com.mustk.task4solution.shared.Constant.SLASH_QUERY_PARAM
import com.mustk.task4solution.shared.Constant.TITLE_QUERY_PARAM
import com.mustk.task4solution.shared.Constant.TYPE_QUERY_PARAM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET(ALL_MOVIE_EXT_URL)
    suspend fun fetchAllMovieData(): Response<Search>
    @GET(SLASH_QUERY_PARAM)
    suspend fun fetchMovieData(
        @Query(IMDB_ID_QUERY_PARAM) imdbId: String
    ): Response<Movie>
    @GET(SLASH_QUERY_PARAM)
    suspend fun searchMovieData(
        @Query(TITLE_QUERY_PARAM) title: String, @Query(TYPE_QUERY_PARAM) type: String
    ): Response<Search>
}