package com.example.movies.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.service.model.MovieModelData
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.repository.MovieDAO

@Database(entities = [MovieModelData::class], version = 1)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDAO() : MovieDAO
    companion object {

        private lateinit var dbInstance: MovieDataBase

        fun getDatabase(context: Context): MovieDataBase {
            if (!::dbInstance.isInitialized) {
                dbInstance =
                    Room.databaseBuilder(context, MovieDataBase::class.java, "movieDB")
                        .allowMainThreadQueries()
                        .build()
            }
            return dbInstance
        }
    }
}
