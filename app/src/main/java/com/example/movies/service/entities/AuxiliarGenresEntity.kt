package com.example.movies.service.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "AuxiliarGenreMovie",
    primaryKeys = ["idGenre", "idMovie"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["title"]
        ),ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["name"]
        )
    ]
)
data class AuxiliarGenresEntity (
        val idMovie : Int,
        val idGenre : Int )
