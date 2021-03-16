package com.test.testproject.data.repositories

import com.test.testproject.data.model.Movie
import com.test.testproject.network.datasource.MoviesRemoteApi
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteApi
) {

    fun getPopularMovies(page: Int): Single<ArrayList<Movie>> {
        return remoteDataSource.getPopularMovies(page).map { it.results }
    }

}