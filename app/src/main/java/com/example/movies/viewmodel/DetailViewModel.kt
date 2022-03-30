package com.example.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.service.repository.remote.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private var mMovieDetailList = MutableLiveData<RemoteDataSource.MovieDetailState>()
    val movieDetailListResponse: LiveData<RemoteDataSource.MovieDetailState> = mMovieDetailList

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            remoteDataSource.getDetail(id)
                .onStart {
                    println("LIPE começou o flow")
                }.catch {
                    println("LIPE deu erro")
                }
                .collect {
                    mMovieDetailList.postValue(it!!)
                }
        }
    }
}





