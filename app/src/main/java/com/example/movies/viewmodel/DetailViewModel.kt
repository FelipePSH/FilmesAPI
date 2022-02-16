package com.example.movies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.service.model.MovieDetailModel
import com.example.movies.service.repository.RemoteRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var mMovieDetailList = MutableLiveData<MovieDetailModel>()
    val movieDetailList: LiveData<MovieDetailModel> = mMovieDetailList

    private val mRemoteRepository = RemoteRepository()

    fun getMovieDetail(id: Int) {
            Log.d("Teste Lipe", "getMovieDetail")
            mRemoteRepository.getDetail(id, {
                mMovieDetailList.value = it
            }, {
                
            })


    }


}
