package com.mustk.task4solution.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mustk.task4solution.databinding.RecyclerMovieRowBinding
import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.util.downloadImageFromUrl

class MovieAdapter(
    private val movies: List<Movie>, private val onMovieSelected: (String) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    class MovieHolder(private val binding: RecyclerMovieRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onMovieSelected: (String) -> Unit) = with(binding) {
            rowMovieImageView.downloadImageFromUrl(movie.posterURL)
            rowMovieNameText.text = movie.title
            rowMovieYearText.text = movie.year
            rowMovieLinearLayout.setOnClickListener {
                movie.imdbID?.let { imdbID ->
                    onMovieSelected(imdbID)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding =
            RecyclerMovieRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position], onMovieSelected)
    }
}