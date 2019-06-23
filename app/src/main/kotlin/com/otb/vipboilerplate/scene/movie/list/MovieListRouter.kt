package com.otb.vipboilerplate.scene.movie.list

import android.content.Intent
import com.otb.vipboilerplate.common.Constants
import com.otb.vipboilerplate.scene.movie.MovieModels
import com.otb.vipboilerplate.scene.movie.details.MovieDetailsActivity

/**
 * Created by Mohit Rajput on 5/5/19.
 */
class MovieListRouter(private val activity: MovieListActivity) : MovieListContract.Router {
    override fun goToMovieDetails(movie: MovieModels.MovieViewModel) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.IntentExtras.MOVIE, movie)
        activity.startActivity(intent)
    }
}
