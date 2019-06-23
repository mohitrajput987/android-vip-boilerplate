package com.otb.vipboilerplate.scene.movie.list

import com.otb.vipboilerplate.R
import com.otb.vipboilerplate.common.Error
import com.otb.vipboilerplate.common.ErrorView
import com.otb.vipboilerplate.common.ErrorViewModel
import com.otb.vipboilerplate.scene.movie.MovieModels

/**
 * Created by Mohit Rajput on 5/5/19.
 */
class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {
    private val errorView: ErrorView = view

    override fun presentMovies(movies: List<MovieModels.Movie>) {
        val isError = movies.isEmpty()
        if (isError) {
            val viewModel = ErrorViewModel(view.getResourcesInstance().getString(R.string.no_movie_found))
            errorView.displayError(viewModel)
        } else {
            val movieViewModels =
                movies.map { MovieModels.MovieViewModel(it.id, it.title, it.posterPath, it.overview, false) }
            val instruction = view.getResourcesInstance().getString(R.string.tap_to_view_details)
            val viewModel = MovieModels.ViewModel(instruction, movieViewModels)
            view.showMovies(viewModel)
        }
    }

    override fun presentError(error: Error) {
        val viewModel = ErrorViewModel(error.message)
        errorView.displayError(viewModel)
    }
}