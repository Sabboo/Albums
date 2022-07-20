package com.saber.albums.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.saber.albums.data.Result
import com.saber.albums.databinding.ActivityMainBinding
import com.saber.albums.ui.adapter.AlbumsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel: AlbumsViewModel by viewModels()

    private lateinit var adapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.albums.observe(this, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        adapter = AlbumsAdapter(it)
                        binding.list.adapter = adapter
                    }
                }
                Result.Status.ERROR -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                Result.Status.LOADING -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
