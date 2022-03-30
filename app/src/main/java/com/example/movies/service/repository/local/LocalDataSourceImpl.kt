package com.example.movies.service.repository.local

import com.example.movies.service.entities.GenreEntity
import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.repository.MovieDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class LocalDataSourceImpl @Inject constructor(
    private val mDataBase: MovieDAO
) : LocalDataSource {

    override fun listMovies(): Flow<List<MovieEntity>> {
        return mDataBase.load()
    }

    override suspend fun saveMovie(movieData: List<MovieEntity>) {
        mDataBase.save(movieData)
    }

    override suspend fun saveGenre(genre: GenreEntity) {
//        mDataBase.saveGenre(genre)
    }

    override suspend fun listGenres() {
//        mDataBase.loadGenres()
    }

}
