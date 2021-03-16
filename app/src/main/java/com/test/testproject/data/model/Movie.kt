package com.test.testproject.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Movie {
    var id: Long? = null
    var title: String? = null
    var adult: Boolean = false

    @JsonProperty("poster_path")
    var posterPath: String? = null

    @JsonProperty("backdrop_path")
    var backdropPath: String? = null
    var video: Boolean = false

    @JsonProperty("genre_ids")
    var genreIds: ArrayList<Long> = ArrayList()

    @JsonProperty("original_language")
    var originalLanguage: String? = null

    @JsonProperty("original_title")
    var originalTitle: String? = null
    var overview: String? = null
    var popularity: Float? = null

    @JsonProperty("release_date")
    var releaseDate: String? = null

    @JsonProperty("vote_count")
    var voteCount: Int = 0

    @JsonProperty("vote_average")
    var voteAverage: Float? = null

}