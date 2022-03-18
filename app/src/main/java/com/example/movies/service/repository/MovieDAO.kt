package com.example.movies.service.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.movies.service.entities.GenreEntity
import com.example.movies.service.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Insert(onConflict = REPLACE)
    fun save(movieData: List<MovieEntity>)

    @Update
    fun update(movieData: MovieEntity)

    @Query("SELECT * FROM Movie")
    fun load(): Flow<List<MovieEntity>>


//    @Insert
//    fun saveGenre(genre: GenreEntity)
//
//    @Update
//    fun updateGenre(genre: GenreEntity)
//
//    @Query( "SELECT * FROM Genre WHERE id = :id")
//    fun getGenre(id: Int)
//
//    @Query("SELECT * FROM Genre")
//    fun loadGenres() : List<GenreEntity>
}
