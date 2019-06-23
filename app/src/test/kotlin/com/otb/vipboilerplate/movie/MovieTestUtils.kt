package com.otb.vipboilerplate.movie

import com.otb.vipboilerplate.data.MovieData
import com.otb.vipboilerplate.scene.movie.MovieModels

/**
 * Created by Mohit Rajput on 27/5/19.
 * Movie unit testing utility
 */
object MovieTestUtils {
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    fun getDummyMovies(totalMovies: Int): List<MovieModels.Movie> {
        val listMovies = ArrayList<MovieModels.Movie>()
        for (i in 1..totalMovies) {
            listMovies.add(
                MovieModels.Movie(
                    id = i,
                    title = "Movie$i",
                    overview = "Movie$i Overview",
                    posterPath = "$POSTER_BASE_URL/movie$i.jpg"
                )
            )
        }
        return listMovies
    }

    fun getDummyMoviesData(totalMovies: Int): List<MovieData> {
        val listMovies = ArrayList<MovieData>()
        for (i in 1..totalMovies) {
            listMovies.add(
                MovieData(
                    id = i,
                    title = "Movie$i",
                    overview = "Movie$i Overview",
                    posterPath = "movie$i.jpg"
                )
            )
        }
        return listMovies
    }
}