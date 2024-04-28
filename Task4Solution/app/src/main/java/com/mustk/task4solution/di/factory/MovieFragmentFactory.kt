package com.mustk.task4solution.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.mustk.task4solution.ui.movie.adapter.MovieAdapter
import com.mustk.task4solution.ui.movie.view.MovieListFragment
import javax.inject.Inject

class MovieFragmentFactory @Inject constructor(private val movieAdapter : MovieAdapter) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            MovieListFragment::class.java.name -> MovieListFragment(movieAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}