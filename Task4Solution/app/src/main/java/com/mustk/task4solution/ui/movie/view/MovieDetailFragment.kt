package com.mustk.task4solution.ui.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.databinding.FragmentMovieDetailBinding
import com.mustk.task4solution.util.downloadImageFromUrl
import com.mustk.task4solution.ui.movie.viewmodel.MovieDetailViewModel
import com.mustk.task4solution.di.factory.MovieViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    @Inject lateinit var movieHelper: MovieDataSource
    private lateinit var binding: FragmentMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieViewModelFactory(movieHelper)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendIdToViewModel()
        observeLiveData()
    }

    private fun sendIdToViewModel() {
        arguments?.let {
            val imdbID = MovieDetailFragmentArgs.fromBundle(it).imdbID
            viewModel.fetchMovieDetailFromAPI(imdbID)
        }
    }

    private fun observeLiveData() = with(binding) {
        viewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
            movieDetail?.let {
                detailLoading.visibility = View.GONE
                detailImageView.visibility = View.VISIBLE
                detailNameText.visibility = View.VISIBLE
                detailYearText.visibility = View.VISIBLE
                detailDirectorText.visibility = View.VISIBLE
                detailActorsText.visibility = View.VISIBLE
                detailCountryText.visibility = View.VISIBLE
                detailImdbText.visibility = View.VISIBLE
                detailImageView.downloadImageFromUrl(movieDetail.posterURL)
                detailNameText.text = movieDetail.title
                detailYearText.text = movieDetail.year
                detailDirectorText.text = movieDetail.director
                detailActorsText.text = movieDetail.actors
                detailCountryText.text = movieDetail.country
                detailImdbText.text = movieDetail.imdbRating
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                detailLoading.visibility = View.GONE
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (isLoading){
                    detailImageView.visibility = View.GONE
                    detailNameText.visibility = View.GONE
                    detailYearText.visibility = View.GONE
                    detailDirectorText.visibility = View.GONE
                    detailActorsText.visibility = View.GONE
                    detailCountryText.visibility = View.GONE
                    detailImdbText.visibility = View.GONE
                    detailLoading.visibility = View.VISIBLE
                }
            }
        }
    }
}