package com.otb.vipboilerplate.network.apiservice

import com.otb.vipboilerplate.network.response.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Mohit Rajput on 5/5/19.
 * Retrofit API interface to fetch movies data
 */
interface MovieApiService {
    companion object {
        const val GET_MOVIES = "movie/popular"
    }

    @GET(GET_MOVIES)
    fun fetchMovies(): Observable<MovieResponse>
}