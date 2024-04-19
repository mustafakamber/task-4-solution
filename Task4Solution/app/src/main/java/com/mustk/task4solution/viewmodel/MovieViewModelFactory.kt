package com.mustk.task4solution.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustk.task4solution.api.ApiHelper

class MovieViewModelFactory(private val movieHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(movieHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
