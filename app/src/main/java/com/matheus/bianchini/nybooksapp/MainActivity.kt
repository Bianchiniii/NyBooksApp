package com.matheus.bianchini.nybooksapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.matheus.bianchini.nybooksapp.databinding.ActivityBooksBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        viewModel = androidx.lifecycle.ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}