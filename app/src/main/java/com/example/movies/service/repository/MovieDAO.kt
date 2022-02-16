package com.example.movies.service.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movies.service.model.MovieModelResponse

@Dao
interface MovieDAO {

    @Insert
    fun save(movieResponse: MovieModelResponse)

    @Update
    fun update(movieResponse: MovieModelResponse)

    @Query("SELECT * FROM Movie")
    fun load() : List<MovieModelResponse>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovie(id: Int) : MovieModelResponse


}
