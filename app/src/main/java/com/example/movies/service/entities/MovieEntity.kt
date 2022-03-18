package com.example.movies.service.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
data class MovieEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "releaseData" )
    val releaseDate: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String?,

    @ColumnInfo(name = "genresId")
    val genresId: Int?

)
