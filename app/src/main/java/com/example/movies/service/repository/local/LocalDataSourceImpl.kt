package com.example.movies.service.repository.local

import android.content.Context
import com.example.movies.service.entities.GenreEntity
import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.repository.APIListener
import kotlinx.coroutines.flow.Flow


class LocalDataSourceImpl(context: Context) : LocalDataSource {
    private val mDataBase = MovieDataBase.getDatabase(context).movieDAO()


    override fun listMovies( ): Flow<List<MovieEntity>> {
        return mDataBase.load()
    }


    override suspend fun saveMovie(movieData: List<MovieEntity>) {
        mDataBase.save(movieData)
    }


    override suspend fun saveGenre(genre: GenreEntity){
//        mDataBase.saveGenre(genre)
    }

    override suspend fun listGenres(){
//        mDataBase.loadGenres()
    }

}
