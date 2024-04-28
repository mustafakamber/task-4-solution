package com.mustk.task4solution.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mustk.task4solution.R
import com.mustk.task4solution.di.factory.MovieFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var fragmentFactory: MovieFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}