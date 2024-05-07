package com.mustk.task4solution.domain

import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.BaseResponse
import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.data.model.Search
import com.mustk.task4solution.data.service.MovieService
import com.mustk.task4solution.shared.Constant.NULL_JSON
import com.mustk.task4solution.shared.Resource
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) :
    MovieDataSource {

    private suspend fun <T> performApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            Resource.loading(null)
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it is BaseResponse && !it.response) {
                        it.error?.let { error ->
                            Resource.error(error, null)
                        } ?: Resource.error(NULL_JSON, null)
                    } else {
                        Resource.success(it)
                    }
                } ?: Resource.error(NULL_JSON, null)
            } else {
                Resource.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun fetchAllMovieData(): Resource<Search> {
        return performApiCall { movieService.fetchAllMovieData() }
    }

    override suspend fun fetchMovieData(imdbId: String): Resource<Movie> {
        return performApiCall { movieService.fetchMovieData(imdbId) }
    }

    override suspend fun searchMovieData(title: String, type: String): Resource<Search> {
        return performApiCall { movieService.searchMovieData(title, type) }
    }
}