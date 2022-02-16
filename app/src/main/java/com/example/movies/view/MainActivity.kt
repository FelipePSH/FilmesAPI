package com.example.movies.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.service.constants.MovieConstants
import com.example.movies.view.recyclermovie.MoviesAdapter
import com.example.movies.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {
    private var id: Int? = null
    private lateinit var mViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Teste Lipe", "OnCreate")

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




        binding.recyclerMovie.layoutManager = LinearLayoutManager(this)
        binding.recyclerMovie.adapter = mAdapter



        observe()



    }

    override fun onStart() {
        super.onStart()
        mViewModel.listMovies()
        Log.d("Teste Lipe", "OnStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Teste Lipe", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Teste Lipe", "OnResume")
        mViewModel.listMovies()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("Teste Lipe", "onSaveInstanceState")


    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("Teste Lipe", "onRestoreInstanceState")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Teste Lipe", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Teste Lipe", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Teste Lipe", "onDestroy")
    }

    private fun observe() {
        mViewModel.moviesList.observe(this, Observer { listMovieModel ->
            Log.d("Teste Lipe", "Movies List observe")
            if (listMovieModel != null) {
                mAdapter.updateList(listMovieModel)
            }
        })

        mViewModel.moviesSearchResult.observe(this, Observer {
            Log.d("Teste Lipe", "observe moviesSearchResult")
            if (it != null) {
                mAdapter.updateList(it)
            }
        })
    }

}
