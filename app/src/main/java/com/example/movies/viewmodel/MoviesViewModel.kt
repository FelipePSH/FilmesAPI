package com.example.movies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.repository.RemoteRepository

class MoviesViewModel (application: Application) : AndroidViewModel(application) {

    private val mMoviesList = MutableLiveData<List<MovieModelResponse>>()
    val moviesList: LiveData<List<MovieModelResponse>> = mMoviesList

    private val mErrorMessage = MutableLiveData<String>()
    val erroMessage: LiveData<String> = mErrorMessage

    private val mMoviesSearchResult = MutableLiveData<List<MovieModelResponse>>()
    val moviesSearchResult: LiveData<List<MovieModelResponse>> = mMoviesSearchResult

    private val mRemoteRepository = RemoteRepository()

    fun listMovies(){
        Log.d("Teste Lipe", "listMovies")
        mRemoteRepository.listMovies({
            mMoviesList.value = it
        }, {errorMessage ->
            mErrorMessage.value = errorMessage

        })
    }

    fun searchMovies(movieName: String){
        mRemoteRepository.searchMovie(movieName, {listMovieModel ->
            mMoviesSearchResult.value = listMovieModel
        }, { errorMessage ->
            mErrorMessage.value = errorMessage
        })
    }


}
