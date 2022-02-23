package com.example.movies.service.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movies.service.model.MovieModelData
import com.example.movies.service.model.MovieModelResponse

@Dao
interface MovieDAO {

    @Insert
    fun save(movieData: MovieModelData)

    @Update
    fun update(movieData: MovieModelData)

    @Query("SELECT * FROM Movie")
    fun load(): List<MovieModelData>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovie(id: Int): MovieModelData


}
