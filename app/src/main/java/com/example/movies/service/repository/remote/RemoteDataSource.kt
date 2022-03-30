package com.example.movies.service.repository.remote

import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.model.GenreResponseModel
import com.example.movies.service.model.MovieDetailModelResponse
import com.example.movies.service.model.MovieModel
import com.example.movies.service.repository.APIListener
import com.example.movies.service.repository.local.LocalDataSource
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieService: MovieService,
    private val localDataSource: LocalDataSource
) {

    suspend fun saveMovies() {
        try {
            val response = movieService.listMovies()
            val movieEntity = response.results?.map {
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    genresId = null
                )
            }


            localDataSource.saveMovie(movieEntity!!)

        } catch (e: Exception) {
            println("LIPE ${e.message}")
        }
    }

    suspend fun getDetail(id: Int) = flow {
        val response = movieService.getDetail(id)
        if (response.isSuccessful) {
            emit(response.body()?.let { MovieDetailState.MovieDetailSuccess(it) })
        } else {
            emit(MovieDetailState.MovieDetailError)
        }
    }

    suspend fun searchMovie(
        movieName: String,
    ) = flow {
        val response = movieService.searchMovie(movieName)
        if (response.isSuccessful) {
            emit(response.body().let {
                it?.results?.let { listmoviemodelresponse ->
                    MovieState.MovieSuccess(listmoviemodelresponse.map {
                        MovieModel(
                            id = it.id,
                            title = it.title,
                            releaseDate = it.releaseDate,
                            posterPath = it.posterPath
                        )
                    })
                }
            })
        } else {
            emit(MovieState.MovieError)
        }
    }

    fun getGenres(listener: APIListener<List<GenreResponseModel>>) {
        val call: Call<List<GenreResponseModel>> = movieService.getGenres()
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

    sealed interface MovieDetailState {
        data class MovieDetailSuccess(val movieDetailModelResponse: MovieDetailModelResponse) :
            MovieDetailState

        object MovieDetailError : MovieDetailState
        object MovieDetailLoading : MovieDetailState
    }

    sealed interface MovieState {
        data class MovieSuccess(val movieDetailModel: List<MovieModel>) : MovieDetailState
        object MovieError : MovieDetailState
        object MovieLoading : MovieDetailState
    }

}





