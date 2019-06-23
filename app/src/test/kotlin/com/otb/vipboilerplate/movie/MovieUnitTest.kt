package com.otb.vipboilerplate.movie

import com.otb.vipboilerplate.RxImmediateSchedulerRule
import com.otb.vipboilerplate.common.ErrorHandler
import com.otb.vipboilerplate.scene.movie.MovieModels
import com.otb.vipboilerplate.scene.movie.list.MovieListContract
import com.otb.vipboilerplate.scene.movie.list.MovieListInteractor
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

/**
 * Unit tests of movie list module.
 *
 */
class MovieUnitTest {
    @Rule
    @JvmField
    val immediateSchedulerRule = RxImmediateSchedulerRule()
    private lateinit var interactor: MovieListContract.Interactor
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var repository: MovieListContract.Repository

    @Before
    fun setup() {
        repository = Mockito.mock(MovieListContract.Repository::class.java)
        presenter = Mockito.mock(MovieListContract.Presenter::class.java)
        interactor = MovieListInteractor(presenter, repository)
    }

    @Test
    fun testLoadMovies_moviesShouldBePresented() {
        val listMovies = MovieTestUtils.getDummyMovies(5)
        `when`(repository.loadMovies()).thenReturn(Observable.just(listMovies))
        interactor.loadMovies()
        verify(presenter).presentMovies(listMovies)
    }

    @Test
    fun testLoadMovies_errorShouldBePresented() {
        val throwable = Throwable("Unexpected error occurred")
        `when`(repository.loadMovies()).thenReturn(Observable.error<List<MovieModels.Movie>>(throwable))
        interactor.loadMovies()
        verify(presenter).presentError(ErrorHandler.getError(throwable))
    }
}