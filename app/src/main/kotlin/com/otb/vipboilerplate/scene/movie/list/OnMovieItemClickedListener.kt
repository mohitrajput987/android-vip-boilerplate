package com.otb.vipboilerplate.scene.movie.list


import com.otb.vipboilerplate.scene.movie.MovieModels

/**
 * Created by Mohit Rajput on 6/5/19.
 */
interface OnMovieItemClickedListener {
    fun onMovieItemClicked(movie: MovieModels.MovieViewModel)
}
