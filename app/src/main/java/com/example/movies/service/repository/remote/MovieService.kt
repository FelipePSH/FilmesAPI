package com.example.movies.service.repository.remote

import com.example.movies.service.model.MovieDetailModel
import com.example.movies.service.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.*

interface MovieService {


    @GET("movie/upcoming")
    fun listMovies(): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getDetail(@Path(value = "movie_id", encoded = true) id: Int) : Call<MovieDetailModel>

    @GET("search/movie")
    fun searchMovie(@Query("query", encoded = true) query: String) : Call<MoviesResponse>


}
