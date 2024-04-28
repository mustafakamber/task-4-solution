package com.mustk.task4solution.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustk.task4solution.shared.Resource
import com.mustk.task4solution.shared.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun <T : Any> safeResponse(
        block: suspend () -> Resource<T>,
        successStatusData: (T) -> Unit
    ) {
        viewModelScope.launch()
        {
            val response = block()
            when (response.status) {
                Status.LOADING -> {
                    _loading.value = true
                }
                Status.ERROR -> {
                    _loading.value = false
                    response.message?.let {
                        _errorMessage.value = it
                    }
                }
                Status.SUCCESS -> {
                    _loading.value = false
                    response.data?.let {
                        successStatusData(it)
                    }
                }
            }
        }
    }
}