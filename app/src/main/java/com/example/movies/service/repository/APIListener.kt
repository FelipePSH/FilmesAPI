package com.example.movies.service.repository

import com.example.movies.service.model.MoviesResponse

interface APIListener<T> {
    fun onSuccess(result: T, statusCode: Int)
    fun onFailure(message: String)
}
