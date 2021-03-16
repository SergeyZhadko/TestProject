package com.test.testproject.network.api

import com.test.testproject.network.answers.AnswerPopularMovies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopular(@Query("api_key")apiKey: String, @Query("page") page: Int): Single<AnswerPopularMovies>

}