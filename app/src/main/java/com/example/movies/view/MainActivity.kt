package com.example.movies.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.view.recyclermovie.MoviesAdapter
import com.example.movies.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel:MoviesViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAdapter = MoviesAdapter { id ->
            val intent =  Intent(this, MovieDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("KEY_MOVIE_ID", id)
            intent.putExtras(bundle)
            startActivity(intent)
        }





        binding.recyclerMovie.layoutManager = LinearLayoutManager(this)
        binding.recyclerMovie.adapter = mAdapter



        observe()


    }

    override fun onResume() {
        super.onResume()
        mViewModel.listMovies()

    }

    fun observe() {
        mViewModel.moviesList.observe(this, Observer { listMovieModel ->
            if (listMovieModel != null){
                mAdapter.updateList(listMovieModel)
            }
        })
    }



}
