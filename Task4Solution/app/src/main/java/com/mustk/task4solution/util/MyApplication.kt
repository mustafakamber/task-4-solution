package com.mustk.task4solution.util

import android.app.Application
import com.mustk.task4solution.api.ApiHelper
import com.mustk.task4solution.api.ApiHelperImp
import com.mustk.task4solution.api.RetrofitBuilder


class MyApplication : Application() {

    lateinit var movieHelper: ApiHelper
    override fun onCreate() {
        super.onCreate()
        initMovieHelper()
    }
    private fun initMovieHelper(){
        val retrofitBuilder = RetrofitBuilder
        movieHelper = ApiHelperImp(retrofitBuilder.movieService)
    }
}
