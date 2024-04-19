package com.mustk.task4solution.api

import com.mustk.task4solution.model.Movie
import com.mustk.task4solution.model.Search
import com.mustk.task4solution.util.Constant.ALL_DATA_URL
import com.mustk.task4solution.util.Constant.IMDB_ID_QUERY_PARAM
import com.mustk.task4solution.util.Constant.SLASH_QUERY_PARAM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ALL_DATA_URL)
    suspend fun fetchAllMovieData(): Response<Search>
    @GET(SLASH_QUERY_PARAM)
    suspend fun fetchMovieData(@Query(IMDB_ID_QUERY_PARAM) imdbId: String?): Response<Movie>
}