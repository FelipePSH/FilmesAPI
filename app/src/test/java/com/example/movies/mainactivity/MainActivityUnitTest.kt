package com.example.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movies.service.model.MovieModel
import com.example.movies.service.repository.remote.RemoteDataSource
import com.example.movies.view.MainActivity
import com.example.movies.view.recyclermovie.MoviesAdapter
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MainActivityTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var mainActivity: MainActivity

    @MockK
    lateinit var mAdapter: MoviesAdapter

    @Before
    fun configure() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when movieListSearchResult liveData receive a success stat, it must update adapter`() {

        val movieModel = MovieModel(
            id = 1,
            title = "Meu filme",
            releaseDate = "1999-7-12",
            posterPath = "foto.png"
        )

        val movieSuccess = RemoteDataSource.MovieState.MovieSuccess(listOf(movieModel))

        //mainActivity.useCase(movieSuccess)

        //mainActivity.mAdapter

        mainActivity.useCase(movieSuccess)

        verify {mainActivity.testUseCase()}

    }
}
