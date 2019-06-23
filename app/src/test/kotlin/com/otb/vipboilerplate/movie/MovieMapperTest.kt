package com.otb.vipboilerplate.movie

import com.otb.vipboilerplate.network.response.MovieResponse
import com.otb.vipboilerplate.scene.movie.list.MovieResponseEntityMapper
import org.junit.Assert
import org.junit.Test

/**
 * Movie modules mappers testing.
 */
class MovieMapperTest {

    @Test
    fun testMoviesMapper_valuesShouldBeMappedAsExpected() {
        val moviesData = MovieTestUtils.getDummyMoviesData(3)
        val movieResponse = MovieResponse(
            page = 0, totalResults = moviesData.size,
            totalPages = 2, movies = moviesData
        )
        val moviesMapper = MovieResponseEntityMapper()
        val movies = moviesMapper.mapFrom(movieResponse)
        for (i in 0 until moviesData.size) {
            val movie = movies!!.get(i)
            val movieData = moviesData[i]
            Assert.assertEquals(movie.id, movieData.id)
            Assert.assertEquals(movie.title, movieData.title)
            Assert.assertEquals(movie.overview, movieData.overview)
            Assert.assertEquals(movie.posterPath, MovieTestUtils.POSTER_BASE_URL + movieData.posterPath)
        }
    }
}