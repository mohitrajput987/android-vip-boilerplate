package com.otb.vipboilerplate.scene.movie.list

import com.otb.vipboilerplate.common.base.Mapper
import com.otb.vipboilerplate.network.response.MovieResponse
import com.otb.vipboilerplate.scene.movie.MovieModels

/**
 * Created by Mohit Rajput on 5/5/19.
 */
class MovieResponseEntityMapper : Mapper<MovieResponse, List<MovieModels.Movie>?> {
    override fun mapFrom(from: MovieResponse): List<MovieModels.Movie>? {
        val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
        val movies = from.movies?.map {
            MovieModels.Movie(
                it.releaseDate,
                it.overview,
                it.adult,
                it.originalTitle,
                POSTER_BASE_URL + it.posterPath,
                it.popularity,
                it.title,
                it.voteAverage,
                it.id,
                it.voteCount
            )
        }
        return movies
    }
}