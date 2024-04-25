package com.mustk.task4solution.ui.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mustk.task4solution.ui.movie.adapter.MovieAdapter
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.databinding.FragmentMovieListBinding
import com.mustk.task4solution.ui.movie.viewmodel.MovieListViewModel
import com.mustk.task4solution.di.factory.MovieViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    @Inject lateinit var movieHelper: MovieDataSource
    private lateinit var binding: FragmentMovieListBinding
    private var movieAdapter = MovieAdapter(arrayListOf()) {}

    private val viewModel: MovieListViewModel by viewModels {
        MovieViewModelFactory(movieHelper)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieListScreen()
        observeLiveData()
    }

    private fun setupMovieListScreen() = with(binding) {
        viewModel.refreshAllMovieData()
        listRecyclerView.adapter = movieAdapter
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.refreshAllMovieData()
        }
    }

    private fun navigateToDetailScreen(imdbId : String){
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(imdbId)
        findNavController().navigate(action)
    }

    private fun observeLiveData() = with(binding) {
        viewModel.movieList.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                swipeRefreshLayout.isRefreshing = false
                listLoading.visibility = View.GONE
                listRecyclerView.visibility = View.VISIBLE
                movieAdapter = MovieAdapter(movies) { imdbId ->
                    navigateToDetailScreen(imdbId)
                }
                listRecyclerView.adapter = movieAdapter
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                listRecyclerView.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
                listLoading.visibility = View.GONE
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (isLoading){
                    listRecyclerView.visibility = View.GONE
                    listLoading.visibility = View.VISIBLE
                }
            }
        }
    }
}