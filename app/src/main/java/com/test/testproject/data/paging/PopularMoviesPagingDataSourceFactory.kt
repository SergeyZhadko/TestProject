package com.test.testproject.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.testproject.data.model.Movie
import com.test.testproject.data.repositories.MoviesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PopularMoviesPagingDataSourceFactory(
    private val moviesRepository: MoviesRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<PopularMoviesPagingDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val popularMoviesDataSource =
            PopularMoviesPagingDataSource(moviesRepository, compositeDisposable)
        sourceLiveData.postValue(popularMoviesDataSource)
        return popularMoviesDataSource
    }

}