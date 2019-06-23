package com.otb.vipboilerplate.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohit Rajput on 5/5/19.
 */
data class MovieData(
    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("genreIds")
    val genreIds: List<Int>? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("popularity")
    val popularity: Double = 0.toDouble(),

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double = 0.toDouble(),

    @SerializedName("video")
    val video: Boolean = false,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("vote_count")
    val voteCount: Int = 0
)
