package com.example.movies.service.repository.local

import com.example.movies.service.entities.GenreEntity
import com.example.movies.service.entities.MovieEntity
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {

    fun listMovies(): Flow<List<MovieEntity>>

    suspend fun saveMovie(movieData: List<MovieEntity>)

    suspend fun saveGenre(genre: GenreEntity)

    suspend fun listGenres()

}
