package com.example.movies.view.recyclermovie

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.RowMovieListBinding
import com.example.movies.service.constants.MovieConstants
import com.example.movies.service.model.MovieModelResponse
import java.text.SimpleDateFormat
import java.util.*


class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = RowMovieListBinding.bind(itemView)
    private val mDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    @SuppressLint("SimpleDateFormat")
    fun bindView(movieResponse: MovieModelResponse, onClick: (Int) -> Unit) = with(binding) {

        textViewMovieTitle.text = movieResponse.title

        val releaseData = SimpleDateFormat("yyyy-MM-dd").parse(movieResponse.releaseDate)
        textViewReleaseDate.text = mDateFormat.format(releaseData)

        Glide.with(itemView.context).load(MovieConstants.URLs.IMAGE_URL + movieResponse.posterPath)
            .into(imageViewMoviePoster)


        root.setOnClickListener {
            onClick.invoke(movieResponse.id)
        }

    }

}
