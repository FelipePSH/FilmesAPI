package com.example.movies.service.repository.remote

import com.example.movies.service.model.MovieDetailModel
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.model.MoviesResponse
import com.example.movies.service.repository.APIListener
import com.example.movies.service.repository.remote.reporitory.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    private val mRemote = RetrofitClient.createService(MovieService::class.java)

    fun listMovies(listener : APIListener<List<MovieModelResponse>>)  {
            val call: Call<MoviesResponse> = mRemote.listMovies()
            call.enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.results?.let { listener.onSuccess(it, response.code()) }
                    } else {
                        listener.onFailure(response.errorBody().toString())

                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    t.message?.let { listener.onFailure(it) }
                }

            })
    }

    fun searchMovie(
        movieName: String,
        onSuccess: (List<MovieModelResponse>) -> Unit,
        onFaiure: (String) -> Unit
    ) {
        val call: Call<MoviesResponse> = mRemote.searchMovie(movieName)
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t.message
            }

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
                t.message
            }
        })

    }


}
