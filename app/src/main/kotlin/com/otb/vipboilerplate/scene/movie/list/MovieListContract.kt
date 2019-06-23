package com.otb.vipboilerplate.scene.movie.list

import com.otb.vipboilerplate.common.Error
import com.otb.vipboilerplate.common.ErrorView
import com.otb.vipboilerplate.common.base.BaseView
import com.otb.vipboilerplate.scene.movie.MovieModels
import io.reactivex.Observable

/**
 * Created by Mohit Rajput on 5/5/19.
 * Contains interfaces of VIP components
 */
class MovieListContract {
    interface View : BaseView, ErrorView {
        fun showMovies(viewModel: MovieModels.ViewModel)
    }

    interface Router {
        fun goToMovieDetails(movie: MovieModels.MovieViewModel)
    }

    interface Presenter {
        fun presentMovies(movies: List<MovieModels.Movie>)
        fun presentError(error: Error)
    }

    interface Interactor {
        fun loadMovies()
        fun searchMovie(request: MovieModels.Request)
        fun clear()
    }

    interface Repository {
        fun loadMovies(): Observable<List<MovieModels.Movie>>
    }
}