package com.test.testproject.network.datasource

import com.test.testproject.network.answers.AnswerPopularMovies
import com.test.testproject.network.api.MoviesApi
import com.test.testproject.utils.API_KEY
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRemoteApi @Inject constructor(
    private val moviesApi: MoviesApi
) {

    fun getPopularMovies(page: Int = 1): Single<AnswerPopularMovies> {
        return moviesApi.getPopular(API_KEY, page)
    }

}