package com.mustk.task4solution.ui.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mustk.task4solution.databinding.FragmentMovieDetailBinding
import com.mustk.task4solution.domain.MovieRepository
import com.mustk.task4solution.ui.movie.viewmodel.MovieDetailViewModel
import com.mustk.task4solution.util.downloadImageFromUrl
import com.mustk.task4solution.util.observe
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    @Inject lateinit var repository: MovieRepository
    private lateinit var binding: FragmentMovieDetailBinding
    lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        sendIdToViewModel()
        observeLiveData()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[MovieDetailViewModel::class.java]
    }

    private fun sendIdToViewModel() {
        arguments?.let {
            val imdbID = MovieDetailFragmentArgs.fromBundle(it).imdbID
            viewModel.fetchMovieDetailFromAPI(imdbID)
        }
    }

    private fun onLoadingChange(loading: Boolean) = with(binding) {
        detailLoading.isVisible = loading
        detailImageView.isVisible = !loading
        detailNameText.isVisible = !loading
        detailYearText.isVisible = !loading
        detailDirectorText.isVisible = !loading
        detailActorsText.isVisible = !loading
        detailCountryText.isVisible = !loading
        detailImdbText.isVisible = !loading
    }

    private fun observeLiveData() = with(binding) {
        observe(viewModel.movieDetail) { movieDetail ->
            with(movieDetail) {
                detailImageView.downloadImageFromUrl(posterURL)
                detailNameText.text = title
                detailYearText.text = year
                detailDirectorText.text = director
                detailActorsText.text = actors
                detailCountryText.text = country
                detailImdbText.text = imdbRating
            }
        }
        observe(viewModel.errorMessage) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        observe(viewModel.loading, ::onLoadingChange)
    }
}