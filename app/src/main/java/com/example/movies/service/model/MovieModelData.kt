package com.example.movies.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
class MovieModelData {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    var title: String = ""

    var releaseDate: String = ""


    var posterPath: String? = ""

}
