package com.example.movies.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.movies.service.model.MovieDetailModel
import com.example.movies.service.model.MovieModel
import com.example.movies.service.model.MovieModelResponse
import com.example.movies.service.model.MoviesResponse
import com.example.movies.service.repository.local.LocalDataSource
import com.example.movies.service.repository.remote.RemoteDataSource
import com.example.movies.util.NetworkChecker
import javax.xml.transform.Transformer


//NAO FAZ UMA REQUISIÇÃO
//MANDA O LOCAL OU O REMOTO FAZER A REQUISIÇÃO PARA ELE

class MovieRepository(val context: Context) {

    private val mRemoteDataSource = RemoteDataSource()
    private val mLocalDataSource = LocalDataSource(context)

    @RequiresApi(Build.VERSION_CODES.M)
    val networkChecker = NetworkChecker(context.getSystemService(ConnectivityManager::class.java))


    fun listMovies(listener : APIListener<List<MovieModelResponse>>){
        if (networkChecker.hasInternet())  {
            mRemoteDataSource.listMovies(listener)

        } else {

            mLocalDataSource.listMovies()

        }
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

    fun getDetail(
        id: Int,
        onSuccess: (MovieDetailModel) -> Unit,
        onFaiure: (String) -> Unit
    ) {

        if (networkChecker.hasInternet()) {
            mRemoteDataSource.getDetail(id, onSuccess, onFaiure)
        } else {
            return
        }


    }

}
