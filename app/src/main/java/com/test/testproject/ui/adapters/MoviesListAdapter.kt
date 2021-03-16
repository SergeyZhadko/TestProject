package com.test.testproject.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.testproject.data.model.Movie
import com.test.testproject.data.paging.PopularMoviesPagingDataSource
import com.test.testproject.databinding.ItemListFooterBinding
import com.test.testproject.databinding.ItemMovieBinding

class MoviesListAdapter(
    private var itemClickListener: (Movie?) -> Unit,
    private var retryClickListener: () -> Unit
) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_MOVIE = 1
        private const val VIEW_TYPE_FOOTER = 2

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

        }

    }

    private var state = PopularMoviesPagingDataSource.State.INIT_LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MOVIE) {
            MovieViewHolder(parent)
        } else {
            FooterViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_MOVIE) {
            (holder as MovieViewHolder).bind(getItem(position), itemClickListener)
        } else if (getItemViewType(position) == VIEW_TYPE_FOOTER) {
            (holder as FooterViewHolder).bind(state, retryClickListener)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) VIEW_TYPE_MOVIE else VIEW_TYPE_FOOTER
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == PopularMoviesPagingDataSource.State.PAGE_LOADING || state == PopularMoviesPagingDataSource.State.PAGE_ERROR)
    }

    fun setState(state: PopularMoviesPagingDataSource.State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    inner class MovieViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemMovieBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?, clickListener: (Movie?) -> Unit) {
            binding.movie = movie
            binding.card.setOnClickListener { clickListener(movie) }
        }

    }

    inner class FooterViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemListFooterBinding = ItemListFooterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(state: PopularMoviesPagingDataSource.State, retryClickListener: () -> Unit) {
            binding.pbLoading.visibility =
                if (state == PopularMoviesPagingDataSource.State.PAGE_LOADING) View.VISIBLE else View.GONE
            binding.btnRetry.visibility =
                if (state == PopularMoviesPagingDataSource.State.PAGE_ERROR) View.VISIBLE else View.GONE
            binding.tvErrorMessage.visibility =
                if (state == PopularMoviesPagingDataSource.State.PAGE_ERROR) View.VISIBLE else View.GONE
            binding.btnRetry.setOnClickListener { retryClickListener() }
        }

    }

}