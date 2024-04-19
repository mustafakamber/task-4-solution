package com.mustk.task4solution.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mustk.task4solution.R
import com.mustk.task4solution.databinding.RecyclerMovieRowBinding
import com.mustk.task4solution.model.Movie
import com.mustk.task4solution.view.MovieListFragmentDirections

class MovieAdapter(
    val movies: MutableList<Movie>,
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>(), MovieClickListener {

    class MovieHolder(val binding: RecyclerMovieRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RecyclerMovieRowBinding>(
            inflater,
            R.layout.recycler_movie_row,
            parent,
            false
        )
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) = with(holder.binding) {
        movieRow = movies[position]
        movieListener = this@MovieAdapter
    }

    fun updateMovieList(newMovies: MutableList<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onMovieClicked(v: View, movieId: String) {
        val action =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId)
        Navigation.findNavController(v).navigate(action)
    }
}