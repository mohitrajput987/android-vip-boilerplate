package com.otb.vipboilerplate.scene.splash

import android.content.Intent
import com.otb.vipboilerplate.scene.movie.list.MovieListActivity

/**
 * Created by Mohit Rajput on 5/5/19.
 */
class SplashRouter(private val activity: SplashActivity) : SplashContract.Router {
    override fun goToMovieList() {
        val intent = Intent(activity, MovieListActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}
