package com.mustk.task4solution.ui.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mustk.task4solution.databinding.FragmentMovieListBinding
import com.mustk.task4solution.domain.MovieRepository
import com.mustk.task4solution.ui.movie.adapter.MovieAdapter
import com.mustk.task4solution.ui.movie.viewmodel.MovieListViewModel
import com.mustk.task4solution.util.observe
import javax.inject.Inject

class MovieListFragment @Inject constructor(private var movieAdapter: MovieAdapter) : Fragment() {

    @Inject
    lateinit var repository: MovieRepository
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupMovieListScreen()
        observeLiveData()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MovieListViewModel::class.java]
    }

    private fun setupMovieListScreen() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.refreshAllMovieData()
        }
        movieAdapter.setOnMovieClickListener { imdbID ->
            navigateToDetailScreen(imdbID)
        }
        listRecyclerView.adapter = movieAdapter
        listSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false
            override fun onQueryTextChange(titleQuery: String): Boolean {
                viewModel.onSearchTextChange(titleQuery)
                return true
            }
        })
    }

    private fun navigateToDetailScreen(imdbId : String){
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(imdbId)
        findNavController().navigate(action)
    }

    private fun observeLiveData() = with(binding) {
        observe(viewModel.movieList) { movies ->
            swipeRefreshLayout.isRefreshing = false
            listLoading.isVisible = false
            listRecyclerView.isVisible = true
            movieAdapter.submitList(movies)
        }
        observe(viewModel.errorMessage) { errorMessage ->
            swipeRefreshLayout.isRefreshing = false
            listLoading.isVisible = false
            listRecyclerView.isVisible = false
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        observe(viewModel.loading) { _ ->
            swipeRefreshLayout.isRefreshing = false
            listRecyclerView.isVisible = false
            listLoading.isVisible = true
        }
    }
}