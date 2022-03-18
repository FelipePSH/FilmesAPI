package com.example.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.service.model.MovieDetailModelResponse
import com.example.movies.service.repository.remote.RemoteDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var mMovieDetailList = MutableLiveData<RemoteDataSource.MovieDetailState>()
    val movieDetailListResponse: LiveData<RemoteDataSource.MovieDetailState> = mMovieDetailList

    private val mRemoteDataSource = RemoteDataSource(application)

     fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            mRemoteDataSource.getDetail(id)
                .onStart {
                    println("LIPE começou o flow")
                }.catch {
                    println("LIPE deu erro")
                }
                .collect {
                    mMovieDetailList.postValue(it)
                }


        }
    }
}





