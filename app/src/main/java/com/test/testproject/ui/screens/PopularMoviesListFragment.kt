package com.test.testproject.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testproject.R
import com.test.testproject.data.paging.PopularMoviesPagingDataSource
import com.test.testproject.ui.adapters.MoviesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_popular_movies.*

@AndroidEntryPoint
class PopularMoviesListFragment : Fragment(R.layout.fragment_popular_movies) {

    companion object {
        fun newInstance() = PopularMoviesListFragment()
    }

    private val viewModel: PopularMoviesListViewModel by viewModels()
    private lateinit var adapter: MoviesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initSwipe()
        initErrorStub()

    }

    private fun initRecycler() {
        adapter = MoviesListAdapter(
            itemClickListener = { viewModel.onItemClicked(it) },
            retryClickListener = { viewModel.retryLoadPage() }
        )

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.moviesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.getState().observe(viewLifecycleOwner) {
            adapter.setState(it)
        }
    }

    private fun initSwipe() {
        swipe.setOnRefreshListener { viewModel.onRefreshList() }
        viewModel.getState().observe(viewLifecycleOwner) {
            swipe.isRefreshing = it == PopularMoviesPagingDataSource.State.INIT_LOADING
        }
    }

    private fun initErrorStub() {
        viewModel.getState().observe(viewLifecycleOwner) {
            stubError.visibility =
                if (it == PopularMoviesPagingDataSource.State.INIT_ERROR) View.VISIBLE else View.GONE
            btnErrorRefresh.setOnClickListener { viewModel.onRefreshList() }
        }
    }

}