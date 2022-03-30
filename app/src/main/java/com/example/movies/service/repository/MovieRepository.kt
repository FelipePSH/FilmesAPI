package com.example.movies.service.repository

import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.repository.local.LocalDataSource
import com.example.movies.service.repository.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


//NAO FAZ UMA REQUISIÇÃO
//MANDA O LOCAL OU O REMOTO FAZER A REQUISIÇÃO PARA ELE

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun listMovies(): Flow<List<MovieEntity>> {
        return localDataSource.listMovies()
    }

    suspend fun saveMovies() {
        //salvar e carregar
        remoteDataSource.saveMovies()
    }

    suspend fun searchMovie(
        movieName: String,
    ) {
        remoteDataSource.searchMovie(movieName)
    }

    suspend fun saveMovies(movieModelData: List<MovieEntity>) {
        localDataSource.saveMovie(movieModelData)
    }
}
