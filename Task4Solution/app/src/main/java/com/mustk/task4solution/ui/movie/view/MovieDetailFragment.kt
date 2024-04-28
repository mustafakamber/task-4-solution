package com.mustk.task4solution.ui.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mustk.task4solution.databinding.FragmentMovieDetailBinding
import com.mustk.task4solution.domain.MovieRepository
import com.mustk.task4solution.ui.movie.viewmodel.MovieDetailViewModel
import com.mustk.task4solution.util.downloadImageFromUrl
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

    private fun updateVisibility(loading: Boolean) {
        val visibility = if (loading) View.GONE else View.VISIBLE
        val oppositeVisibility = if (loading) View.VISIBLE else View.GONE
        with(binding) {
            detailLoading.visibility = oppositeVisibility
            detailImageView.visibility = visibility
            detailNameText.visibility = visibility
            detailYearText.visibility = visibility
            detailDirectorText.visibility = visibility
            detailActorsText.visibility = visibility
            detailCountryText.visibility = visibility
            detailImdbText.visibility = visibility
        }
    }

    private fun observeLiveData() = with(binding) {
        viewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
            movieDetail?.let {
                updateVisibility(false)
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
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                updateVisibility(false)
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                updateVisibility(true)
            }
        }
    }
}