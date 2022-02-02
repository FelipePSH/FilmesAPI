package com.example.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.service.model.MovieModel
import com.example.movies.service.repository.RemoteRepository

class MoviesViewModel (application: Application) : AndroidViewModel(application) {

    private val mMoviesList = MutableLiveData<List<MovieModel>>()
    val moviesList: LiveData<List<MovieModel>> = mMoviesList


    private val mRemoteRepository = RemoteRepository()

    fun listMovies(){
        mRemoteRepository.listMovies({
            mMoviesList.value = it
        }, {
            //implemetar para caso de erro
        })
    }


}
