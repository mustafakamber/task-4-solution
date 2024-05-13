package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.shared.Constant.JOKER_KEY
import com.mustk.task4solution.shared.Constant.TYPE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieDataSource) :
    BaseViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()

    init {
        fetchMovieListFromAPI()
        observeSearchTextChanges()
    }

    private fun observeSearchTextChanges() {
        viewModelScope.launch {
            searchText
                .debounce(500L)
                .distinctUntilChanged()
                .filter {
                    it.isNotEmpty()
                }
                .filter {
                    it.length > 2
                }
                .map {
                    it.lowercase().trim() + JOKER_KEY
                }
                .collectLatest {
                    searchMovieListFromAPI(it)
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun refreshAllMovieData() {
        fetchMovieListFromAPI()
    }

    private fun searchMovieListFromAPI(movieTitle: String) {
        safeRequest(
            response = { repository.searchMovieData(movieTitle, TYPE_KEY) },
            successStatusData = { searchData ->
                _movieList.value = searchData.movies
            }
        )
    }

    private fun fetchMovieListFromAPI() {
        safeRequest(
            response = { repository.fetchAllMovieData() },
            successStatusData = { searchData ->
                _movieList.value = searchData.movies
            }
        )
    }
}