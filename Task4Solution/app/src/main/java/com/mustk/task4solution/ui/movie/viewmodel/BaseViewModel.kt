package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    protected fun <T : Any> handleNetworkFlow(
        response: Flow<Response<T>>,
        fetchResponseBody: (T) -> Unit
    ) {
        _loading.value = true
        viewModelScope.launch {
            response.flowOn(Dispatchers.IO).catch { error ->
                _errorMessage.postValue(error.localizedMessage)
            }.collect { apiResponse ->
                if (apiResponse.isSuccessful) {
                    apiResponse.body()?.let {
                        fetchResponseBody(it)
                    }
                }
            }
        }
    }

    protected fun handleBody(response: Boolean, error: String?, showContent: () -> Unit) {
        if (response) {
            _loading.value = false
            showContent()
        } else {
            error?.let {
                _errorMessage.value = it
            }
        }
    }
}

