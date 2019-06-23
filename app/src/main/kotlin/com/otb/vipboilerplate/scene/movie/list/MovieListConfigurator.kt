package com.otb.vipboilerplate.scene.movie.list

import com.otb.vipboilerplate.common.base.Configurator
import com.otb.vipboilerplate.network.ApiClient

/**
 * Created by Mohit Rajput on 6/5/19.
 */
class MovieListConfigurator : Configurator<MovieListActivity> {
    override fun configure(view: MovieListActivity) {
        val presenter = MovieListPresenter(view)
        val repository = MovieListRepository(ApiClient.movieApiService)
        val interactor = MovieListInteractor(presenter, repository)
        val router = MovieListRouter(view)
        view.interactor = interactor
        view.router = router
    }
}