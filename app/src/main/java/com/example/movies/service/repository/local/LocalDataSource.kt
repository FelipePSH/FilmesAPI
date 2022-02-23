package com.example.movies.service.repository.local

import android.content.Context
import com.example.movies.service.model.MovieModelData
import com.example.movies.service.model.MovieModelResponse


class LocalDataSource(context: Context) {
    private val mDataBase = MovieDataBase.getDatabase(context)


    fun listMovies( ): List<MovieModelData> {
        return mDataBase.movieDAO().load()
    }

    fun getMovie(id: Int): MovieModelData {
        return mDataBase.movieDAO().getMovie(id)
    }

    fun save(movieData: MovieModelData) {
        TODO("Verificar como iremos salvar todos filmes, já que o que recebemos é uma lista de vário filmes")
        mDataBase.movieDAO().save(movieData)
    }

    fun update(movieData: MovieModelData) {
        TODO("Verificar como iremos atualizar todos filmes, já que o que recebemos é uma lista de vário filmes")
        mDataBase.movieDAO().update(movieData)
    }


}
