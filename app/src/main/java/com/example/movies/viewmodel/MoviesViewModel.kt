package com.example.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.service.model.MovieModel
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.repository.APIListener
import com.example.movies.service.repository.MovieRepository

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val mMoviesList = MutableLiveData<List<MovieModel>>()
    val moviesList: LiveData<List<MovieModel>> = mMoviesList

    private val mErrorMessage = MutableLiveData<String>()
    val erroMessage: LiveData<String> = mErrorMessage

    private val mMoviesSearchResult = MutableLiveData<List<MovieModel>>()
    val moviesSearchResult: LiveData<List<MovieModel>> = mMoviesSearchResult

    private val mMovieRepository = MovieRepository(application)

    fun listMovies() {
        mMovieRepository.listMovies(object : APIListener<List<MovieModelResponse>> {
            override fun onSuccess(result: List<MovieModelResponse>, statusCode: Int) {
                val movieModelList = result.map { movieModelResponse ->
                    MovieModel(
                        id = movieModelResponse.id,
                        title = movieModelResponse.title,
                        posterPath = movieModelResponse.posterPath,
                        releaseDate = movieModelResponse.releaseDate
                    )
                }
                mMoviesList.value = movieModelList


            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }

        })

    }


    fun searchMovies(movieName: String) {
        mMovieRepository.searchMovie(movieName, { listMovieModelResponseOrData ->

            val movieModel = listMovieModelResponseOrData.map { movieModelResponse ->
                MovieModel(
                    id = movieModelResponse.id,
                    title = movieModelResponse.title,
                    posterPath = movieModelResponse.posterPath,
                    releaseDate = movieModelResponse.releaseDate
                )
            }
            mMoviesSearchResult.value = movieModel
        }, { errorMessage ->
            mErrorMessage.value = errorMessage
        })
    }


}
