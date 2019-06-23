package com.otb.vipboilerplate.scene.movie.list

import com.otb.vipboilerplate.common.DisposableCollector
import com.otb.vipboilerplate.common.ErrorHandler
import com.otb.vipboilerplate.scene.movie.MovieModels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mohit Rajput on 5/5/19.
 */

class MovieListInteractor(
    private val presenter: MovieListContract.Presenter,
    private val repository: MovieListContract.Repository
) : MovieListContract.Interactor {

    private val disposableCollector = DisposableCollector()

    override fun loadMovies() {
        val moviesObservable = repository.loadMovies()
        val stepSubscribe = moviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> handleLoadMoviesSuccess(result) },
                { throwable -> handleError(throwable) }
            )
        disposableCollector.addDisposable(stepSubscribe)
    }

    private fun handleLoadMoviesSuccess(movies: List<MovieModels.Movie>) {
        presenter.presentMovies(movies)
    }

    private fun handleError(throwable: Throwable) {
        presenter.presentError(ErrorHandler.getError(throwable))
    }

    override fun searchMovie(request: MovieModels.Request) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        disposableCollector.clearDisposables()
    }
}
