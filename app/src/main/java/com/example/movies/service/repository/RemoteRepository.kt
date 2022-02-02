package com.example.movies.service.repository

import com.example.movies.service.MovieService
import com.example.movies.service.model.MovieDetailModel
import com.example.movies.service.model.MovieModel
import com.example.movies.service.model.MoviesResponse
import com.example.movies.service.repository.remote.reporitory.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {
    private val mRemote = RetrofitClient.createService(MovieService::class.java)


    fun listMovies(
        onSuccess: (List<MovieModel>) -> Unit,
        onFaiure: (String) -> Unit
    ) {
        val call: Call<MoviesResponse> = mRemote.listMovies()
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { onSuccess.invoke(it) }
                } else {
                    onFaiure.invoke(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t.message
            }

        })

    }

    fun getDetail(
        id: Int,
        onSuccess: (MovieDetailModel) -> Unit,
        onFaiure: (String) -> Unit
    ) {
        val call: Call<MovieDetailModel> = mRemote.getDetail(id)
        call.enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    onFaiure.invoke(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }


}
