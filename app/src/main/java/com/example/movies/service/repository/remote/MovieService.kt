package com.example.movies.service.repository.remote

import com.example.movies.service.model.GenreResponseModel
import com.example.movies.service.model.MovieDetailModelResponse
import com.example.movies.service.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieService {


    @GET("movie/upcoming")
    suspend fun listMovies(): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getDetail(@Path(value = "movie_id", encoded = true) id: Int) : Response<MovieDetailModelResponse>

    @GET("search/movie")
    fun searchMovie(@Query("query", encoded = true) query: String) : Call<MoviesResponse>


    @GET("genre/movie/list")
    fun getGenres(): Call<List<GenreResponseModel>>

}
