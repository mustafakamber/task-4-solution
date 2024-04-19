package com.mustk.task4solution.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mustk.task4solution.adapter.MovieAdapter
import com.mustk.task4solution.databinding.FragmentMovieListBinding
import com.mustk.task4solution.model.Movie
import com.mustk.task4solution.util.MyApplication
import com.mustk.task4solution.viewmodel.MovieViewModel
import com.mustk.task4solution.viewmodel.MovieViewModelFactory

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private var movieAdapter = MovieAdapter(arrayListOf())
    private var filteredMovies = ArrayList<Movie>()

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((requireActivity().application as MyApplication).movieHelper)
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
            listRecyclerView.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = true
            viewModel.refreshAllMovieData()
        }
    }

    private fun observeLiveData() = with(binding) {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                listRecyclerView.visibility = View.VISIBLE
                swipeRefreshLayout.isRefreshing = false
                movieAdapter.updateMovieList(movies.toMutableList())
                listSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean = false
                    override fun onQueryTextChange(queryText: String?): Boolean {
                        if (queryText != null) {
                            filteredMovies.clear()
                            val searchText = queryText.lowercase()
                            movies.forEach { movie ->
                                movie.title?.let { movieTitle ->
                                    if (movieTitle.lowercase().contains(searchText)) {
                                        filteredMovies.add(movie)
                                        movieAdapter.updateMovieList(filteredMovies)
                                    }
                                }
                            }
                        }
                        return true
                    }
                })
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}