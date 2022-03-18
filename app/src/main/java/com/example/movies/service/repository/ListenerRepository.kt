package com.example.movies.service.repository

import com.example.movies.service.model.MovieModel

interface ListenerRepository {
    fun onSuccess(result: List<MovieModel>)
    fun onFailure(message: String)
}
