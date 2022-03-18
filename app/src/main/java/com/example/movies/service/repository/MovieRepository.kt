package com.example.movies.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.movies.service.entities.MovieEntity
import com.example.movies.service.model.MovieModel
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.repository.local.LocalDataSourceImpl
import com.example.movies.service.repository.remote.RemoteDataSource
import com.example.movies.util.NetworkChecker
import kotlinx.coroutines.flow.Flow


//NAO FAZ UMA REQUISIÇÃO
//MANDA O LOCAL OU O REMOTO FAZER A REQUISIÇÃO PARA ELE

class MovieRepository(val context: Context) {

    private val mRemoteDataSource = RemoteDataSource(context)
    private val mLocalDataSource = LocalDataSourceImpl(context)

    @RequiresApi(Build.VERSION_CODES.M)
    val networkChecker = NetworkChecker(context.getSystemService(ConnectivityManager::class.java))


    fun listMovies(): Flow<List<MovieEntity>> {
        return mLocalDataSource.listMovies()
    }


    suspend fun saveMovies() {
        //salvar e carregar
        mRemoteDataSource.saveMovies()
    }

    fun searchMovie(
        movieName: String,
        onSuccess: (List<MovieModelResponse>) -> Unit,
        onFaiure: (String) -> Unit
    ) {
        if (networkChecker.hasInternet()) {
            mRemoteDataSource.searchMovie(
                movieName, onSuccess, onFaiure
            )
        } else {
            return
        }

    }

    suspend fun saveMovies(movieModelData: List<MovieEntity>) {
        mLocalDataSource.saveMovie(movieModelData)
    }
}


sealed class ListState {
    data class Error(val message: String) : ListState()

    data class Loaded(val listMovies: List<MovieModel>) : ListState()

    object Loading : ListState()
}
