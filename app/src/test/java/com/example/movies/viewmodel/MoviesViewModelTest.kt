package com.example.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.model.MovieModel
import com.example.movies.service.repository.MovieRepository
import com.example.movies.service.repository.remote.RemoteDataSource
import com.example.movies.viewmodel.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var moviesRepository: MovieRepository

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @InjectMockKs
    lateinit var viewModel: MoviesViewModel

    @Test
    fun `when viewModel fetch data then it should call the repository`() {

        val mockedMovie = MovieEntity(
            id = 1,
            title = "Meu filme",
            releaseDate = "1999-7-12",
            posterPath = "foto.png",
            genresId = 3
        )

        val movieModel = listOf(mockedMovie).map { movie ->
            MovieModel(
                id = movie.id,
                title = movie.title,
                releaseDate = movie.releaseDate,
                posterPath = movie.posterPath

            )
        }

        every {
            moviesRepository.listMovies()
        } returns flowOf(listOf(mockedMovie))

        viewModel.listMovies()

        assertEquals(viewModel.moviesList.getOrAwaitValue(), movieModel)

        verify { moviesRepository.listMovies() }
    }

    @Before
    fun configureMockk() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
}


