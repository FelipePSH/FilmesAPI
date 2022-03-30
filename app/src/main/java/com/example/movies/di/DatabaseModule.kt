package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.service.repository.MovieDAO
import com.example.movies.service.repository.local.LocalDataSource
import com.example.movies.service.repository.local.LocalDataSourceImpl
import com.example.movies.service.repository.local.MovieDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModule {

    @Binds
    abstract fun bindsLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext appContext: Context): MovieDataBase {
            return Room.databaseBuilder(
                appContext,
                MovieDataBase::class.java,
                "movieDB"
            ).build()
        }

        @Provides
        fun provideMovieDao(database: MovieDataBase): MovieDAO {
            return database.movieDAO()
        }
    }
}
