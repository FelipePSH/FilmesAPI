package com.example.movies.service.repository.remote

import android.content.Context
import android.util.Log
import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.model.GenreResponseModel
import com.example.movies.service.model.MovieDetailModelResponse
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.model.MoviesResponse
import com.example.movies.service.repository.APIListener
import com.example.movies.service.repository.local.LocalDataSourceImpl
import com.example.movies.service.repository.remote.reporitory.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RemoteDataSource(context: Context) {

    private val mRemote = RetrofitClient.createService(MovieService::class.java)
    private val mLocalDataSource = LocalDataSourceImpl(context)


    suspend fun saveMovies(){
        try {
           val response = mRemote.listMovies()

            val movieEntity = response.results?.map {
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    genresId = null
                )
            }

            mLocalDataSource.saveMovie(movieEntity!!)

        } catch (e : Exception) {
            println("LIPE ${e.message}")
        }
    }


    suspend fun getDetail(id: Int) = flow {
        val response = mRemote.getDetail(id)
            if (response.isSuccessful) {
                emit(response.body()?.let { MovieDetailState.MovieDetailSuccess(it) })
            } else {
                emit(MovieDetailState.MovieDetailError)
            }

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



    fun getGenres(listener : APIListener<List<GenreResponseModel>>){
        val call: Call<List<GenreResponseModel>> = mRemote.getGenres()
        call.enqueue(object : Callback<List<GenreResponseModel>> {
            override fun onResponse(
                call: Call<List<GenreResponseModel>>,
                response: Response<List<GenreResponseModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                } else {
                    listener.onFailure(response.errorBody().toString())

                }
            }

            override fun onFailure(call: Call<List<GenreResponseModel>>, t: Throwable) {
                return
            }
        })
    }

    sealed interface MovieDetailState{
        data class MovieDetailSuccess(val movieDetailModelResponse: MovieDetailModelResponse) : MovieDetailState
        object MovieDetailError : MovieDetailState
        object MovieDetailLoading : MovieDetailState
    }
}





