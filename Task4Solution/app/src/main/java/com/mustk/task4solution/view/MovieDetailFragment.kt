package com.mustk.task4solution.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mustk.task4solution.R
import com.mustk.task4solution.databinding.FragmentMovieDetailBinding
import com.mustk.task4solution.util.MyApplication
import com.mustk.task4solution.viewmodel.MovieViewModel
import com.mustk.task4solution.viewmodel.MovieViewModelFactory

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((requireActivity().application as MyApplication).movieHelper)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieDetailScreen()
        observeLiveData()
    }

    private fun setupMovieDetailScreen() = with(binding) {
        arguments?.let {
            val imdbID = MovieDetailFragmentArgs.fromBundle(it).imdbID
            viewModel.fetchMovieDetailFromAPI(imdbID)
        }
    }

    private fun observeLiveData() = with(binding) {
        viewModel.movieDetails.observe(viewLifecycleOwner) { movieDetail ->
            movieDetail?.let {
                selectedMovieDetail = movieDetail
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}