package com.mustk.task4solution.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mustk.task4solution.data.model.Movie
import com.mustk.task4solution.databinding.RecyclerMovieRowBinding
import com.mustk.task4solution.util.downloadImageFromUrl
import javax.inject.Inject

class MovieAdapter @Inject constructor() :
    ListAdapter<Movie, MovieAdapter.MovieHolder>(MovieDiffCallback()) {

    private var onMovieClickListener: ((String) -> Unit)? = null

    inner class MovieHolder(private val binding: RecyclerMovieRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            rowMovieImageView.downloadImageFromUrl(movie.posterURL)
            rowMovieNameText.text = movie.title
            rowMovieYearText.text = movie.year
            rowMovieLinearLayout.setOnClickListener {
                movie.imdbID?.let { imdbID ->
                    onMovieClickListener?.let {
                        it(imdbID)
                    }
                }
            }
        }
    }

    fun setOnMovieClickListener(listener: (String) -> Unit) {
        onMovieClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding =
            RecyclerMovieRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}