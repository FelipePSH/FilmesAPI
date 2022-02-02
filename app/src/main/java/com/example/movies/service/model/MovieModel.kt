package com.example.movies.service.model

import com.google.gson.annotations.SerializedName


data class MoviesResponse (
    val results: List<MovieModel>?
    )




class MovieModel {

    @SerializedName("id")
    var id: Int = 0
    @SerializedName("title")
    var title: String = ""

    @SerializedName("release_date")
    var releaseDate: String = ""

    @SerializedName("poster_path")
    var posterPath: String? = null


}

