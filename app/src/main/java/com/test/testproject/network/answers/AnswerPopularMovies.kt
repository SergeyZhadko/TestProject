package com.test.testproject.network.answers

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.test.testproject.data.model.Movie

@JsonIgnoreProperties(ignoreUnknown = true)
class AnswerPopularMovies {
    var page: Int? = null
    var results: ArrayList<Movie> = ArrayList()
    @JsonProperty("total_pages")
    var totalPages: Int? = null
    @JsonProperty("total_results")
    var totalResults: Int? = null
}