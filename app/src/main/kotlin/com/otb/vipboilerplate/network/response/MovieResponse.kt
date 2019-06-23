package com.otb.vipboilerplate.network.response

import com.google.gson.annotations.SerializedName
import com.otb.vipboilerplate.data.MovieData

/**
 * Created by Mohit Rajput on 5/5/19.
 */
data class MovieResponse(
    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("total_results")
    val totalResults: Int = 0,

    @SerializedName("total_pages")
    val totalPages: Int = 0,

    @SerializedName("results")
    val movies: List<MovieData>? = null
)