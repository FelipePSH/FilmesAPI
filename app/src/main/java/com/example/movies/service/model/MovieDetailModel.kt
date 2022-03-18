package com.example.movies.service.model

data class MovieDetailModel(


    val id: Int,


    val genres: List<MovieGenreResponse>,


    val overview: String,

    val title: String,


    val backdropPath: String?,


    val posterPath: String?,


    val releaseDate: String
)
