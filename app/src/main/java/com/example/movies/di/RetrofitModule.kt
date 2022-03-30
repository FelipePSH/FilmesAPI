package com.example.movies.di

import com.example.movies.service.repository.remote.MovieService
import com.example.movies.service.repository.remote.reporitory.CustomInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/ "

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    @Singleton
    fun providesMovieService(): MovieService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(CustomInterceptor()).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

}
