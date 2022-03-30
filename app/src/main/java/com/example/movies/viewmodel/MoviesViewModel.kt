package com.example.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.service.model.MovieModel
import com.example.movies.service.repository.MovieRepository
import com.example.movies.service.repository.remote.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val mMoviesList = MutableLiveData<List<MovieModel>>()
    val moviesList: LiveData<List<MovieModel>> = mMoviesList

    private var mErrorMessage = MutableLiveData<String>()
    val erroMessage: LiveData<String> = mErrorMessage

    private var mMovieListSearchResult = MutableLiveData<RemoteDataSource.MovieDetailState>()
    val movieListSearchResult: LiveData<RemoteDataSource.MovieDetailState> = mMovieListSearchResult

    fun listMovies() {
        viewModelScope.launch {
            movieRepository.listMovies()
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
        viewModelScope.launch {
            remoteDataSource.searchMovie(movieName)
                .collect {
                    mMovieListSearchResult.postValue(it)
                }
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            movieRepository.saveMovies()
        }
    }

    fun start() {
        listMovies()
        getMovies()
    }

}



