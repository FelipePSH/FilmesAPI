package com.example.movies.service.repository

interface APIListener<T> {
    fun onSuccess(result: T)
    fun onFailure(message: String)
}
