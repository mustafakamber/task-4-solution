package com.mustk.task4solution.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.ui.movie.viewmodel.MovieDetailViewModel
import com.mustk.task4solution.ui.movie.viewmodel.MovieListViewModel
import javax.inject.Inject

class MovieViewModelFactory @Inject constructor(private val movieHelper: MovieDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(movieHelper) as T
        }
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
