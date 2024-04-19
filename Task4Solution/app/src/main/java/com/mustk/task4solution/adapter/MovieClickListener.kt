package com.mustk.task4solution.adapter

import android.view.View

interface MovieClickListener {
    fun onMovieClicked(v: View, movieId: String)
}