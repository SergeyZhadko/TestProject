package com.test.testproject.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.terrakok.cicerone.Router
import com.test.testproject.data.model.Movie
import com.test.testproject.data.paging.PopularMoviesPagingDataSource
import com.test.testproject.data.paging.PopularMoviesPagingDataSourceFactory
import com.test.testproject.data.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class PopularMoviesListViewModel @Inject constructor(
    private val router: Router,
    moviesRepository: MoviesRepository
) : ViewModel() {

    companion object {
        const val PER_PAGE = 20
    }

    var moviesList: LiveData<PagedList<Movie>>

    private val compositeDisposable = CompositeDisposable()
    private val popularMoviesDataSourceFactory: PopularMoviesPagingDataSourceFactory

    init {
        popularMoviesDataSourceFactory =
            PopularMoviesPagingDataSourceFactory(moviesRepository, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setPageSize(PER_PAGE)
            .setInitialLoadSizeHint(PER_PAGE * 2)
            .setEnablePlaceholders(false)
            .build()

        moviesList = LivePagedListBuilder(popularMoviesDataSourceFactory, config).build()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun onRefreshList() {
        popularMoviesDataSourceFactory.sourceLiveData.value?.invalidate()
    }

    fun getState(): LiveData<PopularMoviesPagingDataSource.State> =
        Transformations.switchMap(
            popularMoviesDataSourceFactory.sourceLiveData,
            PopularMoviesPagingDataSource::state
        )

    fun onItemClicked(movie: Movie?) {
        movie?.let {
            //TODO router.navigateTo(Screens.movieInfo(it))
        }
    }

    fun retryLoadPage() {
        popularMoviesDataSourceFactory.sourceLiveData.value?.retry()
    }

}