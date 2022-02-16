package com.example.movies.view.recyclermovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.service.model.MovieModelResponse

class MoviesAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<MoviesViewHolder>() {

    private var mList: List<MovieModelResponse> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_movie_list, parent, false)
        return MoviesViewHolder(item)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindView(mList[position], onClick)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateList(list: List<MovieModelResponse>) {
        mList = list
        notifyDataSetChanged()
    }



}
