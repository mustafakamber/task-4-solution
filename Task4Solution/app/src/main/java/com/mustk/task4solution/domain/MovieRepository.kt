package com.mustk.task4solution.domain

import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.data.service.MovieService
import com.mustk.task4solution.shared.Constant.NULL_JSON
import com.mustk.task4solution.shared.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) :
    MovieDataSource {
    override suspend fun fetchAllMovieData(): Resource<List<Movie>> {
        return try {
            Resource.loading(null)
            val response = movieService.fetchAllMovieData()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.response) {
                        return@let Resource.success(it.search)
                    } else {
                        it.error?.let {
                            Resource.error(it, null)
                        }
                    }
                } ?: Resource.error(NULL_JSON, null)
            } else {
                Resource.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun fetchMovieData(imdbId: String): Resource<Movie> {
        return try {
            Resource.loading(null)
            val response = movieService.fetchMovieData(imdbId)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.response) {
                        return@let Resource.success(it)
                    } else {
                        it.error?.let {
                            Resource.error(it, null)
                        }
                    }
                } ?: Resource.error(NULL_JSON, null)
            } else {
                Resource.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun searchMovieData(title: String, type: String): Resource<List<Movie>> {
        return try {
            Resource.loading(null)
            val response = movieService.searchMovieData(title, type)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.response) {
                        return@let Resource.success(it.search)
                    } else {
                        it.error?.let {
                            Resource.error(it, null)
                        }
                    }
                } ?: Resource.error(NULL_JSON, null)
            } else {
                Resource.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }
}

