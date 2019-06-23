package com.otb.vipboilerplate.scene.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Mohit Rajput on 5/5/19.
 * Contains Business, ViewModels and Request related models for movies list
 */
class MovieModels {
    data class Movie(
        val releaseDate: String? = null,
        val overview: String? = null,
        val adult: Boolean = false,
        val originalTitle: String? = null,
        val posterPath: String? = null,
        val popularity: Double = 0.toDouble(),
        val title: String? = null,
        val voteAverage: Double = 0.toDouble(),
        val id: Int = 0,
        val voteCount: Int = 0
    )

    data class ViewModel(
        val instruction: String,
        val movies: List<MovieViewModel>
    )

    @Parcelize
    data class MovieViewModel(
        val id: Int?,
        val title: String?,
        val posterPath: String?,
        val overview: String?,
        val isSelected: Boolean
    ) : Parcelable

    data class Request(
        val searchKeyword: String
    )
}