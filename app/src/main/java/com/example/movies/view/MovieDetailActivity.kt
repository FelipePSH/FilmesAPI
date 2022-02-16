package com.example.movies.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movies.databinding.ActivityMovieDatailBinding
import com.example.movies.service.constants.MovieConstants
import com.example.movies.service.model.MovieDetailModel
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
        mViewModel.movieDetailList.observe(this, Observer { ListMovieDetail ->
            if (ListMovieDetail != null){
                onReceiveList(ListMovieDetail)
            }
        })

    }

    private fun onReceiveList(listMovieDetail: MovieDetailModel) {
        binding.textViewMovieTitle.text = listMovieDetail.title
        binding.textViewOverview.text = listMovieDetail.overview

        val separator = ", "
        binding.textViewGenre.text = "Gêneros: " + listMovieDetail.convetGenreToStringList().joinToString(separator)

        if (!listMovieDetail.backdropPath.isNullOrEmpty()){
        Glide.with(this).load(MovieConstants.URLs.IMAGE_URL + listMovieDetail.backdropPath)
            .into(binding.imageViewBackdropPoster)
        } else {
            Glide.with(this).load(MovieConstants.URLs.IMAGE_URL + listMovieDetail.posterPath)
                .into(binding.imageViewBackdropPoster)

            }


        binding.textViewReleaseDate.text = parseRealese(listMovieDetail.releaseDate)

    }


    fun parseRealese(receivedReleaseDate: String): String {

        val mDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val releaseDate = SimpleDateFormat("yyyy-MM-dd").parse(receivedReleaseDate)
        return mDateFormat.format(releaseDate)

    }


}

