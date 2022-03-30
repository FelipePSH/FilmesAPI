package com.example.movies.view

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.service.constants.MovieConstants
import com.example.movies.service.repository.remote.RemoteDataSource
import com.example.movies.view.recyclermovie.MoviesAdapter
import com.example.movies.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAdapter = MoviesAdapter { id ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(MovieConstants.BUNDLE.MOVIE_KEY_ID, id)
            intent.putExtras(bundle)
            startActivity(intent)
        }


        mViewModel.start()

        binding.recyclerMovie.layoutManager = LinearLayoutManager(this)
        binding.recyclerMovie.adapter = mAdapter

        observe()

    }

    override fun onResume() {
        super.onResume()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()

                if (query != null) {
                    mViewModel.searchMovies(query)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Por favor, digite o nome do filme desejado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    mViewModel.searchMovies(query)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Por favor, digite o nome do filme desejado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true

            }

        })

    }




    private fun observe() {
        mViewModel.moviesList.observe(this, { listMovieModel ->

            if (listMovieModel != null) {
                mAdapter.updateList(listMovieModel)
            }
        })

        mViewModel.movieListSearchResult.observe(this, { listMovieSearchResult ->
            useCase(listMovieSearchResult)
        })
    }

    fun useCase(listMovieSearchResult: RemoteDataSource.MovieDetailState?) {
        testUseCase()
//        when (listMovieSearchResult) {
//            RemoteDataSource.MovieState.MovieError -> {
//                Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
//            }
//            RemoteDataSource.MovieState.MovieLoading -> {
//                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
//            }
//            is RemoteDataSource.MovieState.MovieSuccess -> {
//                mAdapter.updateList(listMovieSearchResult.movieDetailModel!!)
//            }
//            else -> {
//                Toast.makeText(this, "erro inesperado", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    fun testUseCase(){

    }

}
