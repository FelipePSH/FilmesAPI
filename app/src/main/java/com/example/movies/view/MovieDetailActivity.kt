package com.example.movies.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies.databinding.ActivityMovieDatailBinding
import com.example.movies.service.constants.MovieConstants
import com.example.movies.service.model.MovieDetailModelResponse
import com.example.movies.service.repository.remote.RemoteDataSource
import com.example.movies.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {
    private var id: Int? = null
    private lateinit var mViewModel: DetailViewModel
    private lateinit var binding: ActivityMovieDatailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Teste Lipe", "onCreate")

        mViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        binding = ActivityMovieDatailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent.extras?.let { bundle ->

            id = bundle.getInt("KEY_MOVIE_ID")
            Log.d("Teste Lipe", "$id movie bundle")
            mViewModel.getMovieDetail(id!!)


        }



        observe()
    }

    private fun observe() {
        mViewModel.movieDetailListResponse.observe(this, Observer { ListMovieDetail ->
            when(ListMovieDetail){
                RemoteDataSource.MovieDetailState.MovieDetailError -> {
                    Toast.makeText(this,"erro", Toast.LENGTH_SHORT)
                }
                RemoteDataSource.MovieDetailState.MovieDetailLoading -> {
                    Toast.makeText(this,"loading", Toast.LENGTH_SHORT)
                }
                is RemoteDataSource.MovieDetailState.MovieDetailSuccess ->{
                    onReceiveList(ListMovieDetail.movieDetailModelResponse)
                }
                else -> {
                    Toast.makeText(this,"erro inesperado", Toast.LENGTH_SHORT)
                }
            }


        })

    }

    private fun onReceiveList(listMovieDetailResponse: MovieDetailModelResponse) {
        binding.textViewMovieTitle.text = listMovieDetailResponse.title
        binding.textViewOverview.text = listMovieDetailResponse.overview

        val separator = ", "
        binding.textViewGenre.text =
            "Gêneros: " + listMovieDetailResponse.convertGenreToStringList()?.joinToString(separator)

        if (!listMovieDetailResponse.backdropPath.isNullOrEmpty()) {
            Glide.with(this)
                .load(MovieConstants.URLs.IMAGE_URL + listMovieDetailResponse.backdropPath)
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
                .into(binding.imageViewBackdropPoster)
        } else {
            Glide.with(this)
                .load(MovieConstants.URLs.IMAGE_URL + listMovieDetailResponse.posterPath)
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
                .into(binding.imageViewBackdropPoster)

        }


        binding.textViewReleaseDate.text = parseRealese(listMovieDetailResponse.releaseDate)

    }


    fun parseRealese(receivedReleaseDate: String): String {

        val mDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val releaseDate = SimpleDateFormat("yyyy-MM-dd").parse(receivedReleaseDate)
        return mDateFormat.format(releaseDate)

    }


}

