package com.example.movies.service.model

import com.google.gson.annotations.SerializedName


data class MovieDetailModel(

    val genres: List<MovieGenreResponse>,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String
) {
    fun convetGenreToStringList(): List<String>{
       return genres.map { genre ->
            genre.name
        }

    }
}


data class MovieGenreResponse(
    val name: String
)
