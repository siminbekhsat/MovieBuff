package com.cna.mobileprogramming.movieBuff.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cna.mobileprogramming.foodie.databinding.ActivityMainBinding
import com.cna.mobileprogramming.movieBuff.adapter.MovieAdapter
import com.cna.mobileprogramming.movieBuff.repository.MainRepository
import com.cna.mobileprogramming.movieBuff.service.RetrofitService
import com.cna.mobileprogramming.movieBuff.viewmodel.MainViewModel
import com.cna.mobileprogramming.movieBuff.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val adapter = MovieAdapter()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)


        viewModel.movieList.observe(this) {
            adapter.setMovies(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllMovies()
        viewModel.getHighRatedMovies()

    }
}