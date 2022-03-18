package com.example.movies.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.movies.service.model.MovieModel
import com.example.movies.service.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val mMoviesList = MutableLiveData<List<MovieModel>>()
    val moviesList: LiveData<List<MovieModel>> = mMoviesList

    private var mErrorMessage = MutableLiveData<String>()
    val erroMessage: LiveData<String> = mErrorMessage

    private val mMoviesSearchResult = MutableLiveData<List<MovieModel>>()
    val moviesSearchResult: LiveData<List<MovieModel>> = mMoviesSearchResult

    private val mMovieRepository = MovieRepository(application)

    private fun listMovies() {
        viewModelScope.launch {
            mMovieRepository.listMovies()
                .onStart {
                    println("LIPE começou o flow")
                }.catch {
                    println("LIPE deu erro")
                }
                .collect {
                    val listMovies = it.map { movie ->
                        MovieModel(
                            id = movie.id,
                            title = movie.title,
                            releaseDate = movie.releaseDate,
                            posterPath = movie.posterPath

                        )
                    }

                    mMoviesList.postValue(listMovies)
                }
        }
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

    private fun getMovies() {
        viewModelScope.launch {
            mMovieRepository.saveMovies()
        }
    }

    fun start(){
        listMovies()
        getMovies()
    }



}



