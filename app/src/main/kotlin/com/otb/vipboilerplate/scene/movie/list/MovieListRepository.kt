package com.otb.vipboilerplate.scene.movie.list

import com.otb.vipboilerplate.network.apiservice.MovieApiService
import com.otb.vipboilerplate.scene.movie.MovieModels
import io.reactivex.Observable

/**
 * Created by Mohit Rajput on 5/5/19.
 */
class MovieListRepository(private val movieApiService: MovieApiService) : MovieListContract.Repository {
    override fun loadMovies(): Observable<List<MovieModels.Movie>> {
        val mapper = MovieResponseEntityMapper()
        val movieResponse = movieApiService.fetchMovies()
        return movieResponse.map(mapper::mapFrom)
    }
}