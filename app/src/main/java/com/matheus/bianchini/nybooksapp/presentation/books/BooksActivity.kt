package com.matheus.bianchini.nybooksapp.presentation.books

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matheus.bianchini.nybooksapp.databinding.ActivityBooksBinding

class BooksActivity : AppCompatActivity() {
    private var _binding: ActivityBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BooksViewModel

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)

        viewModel = androidx.lifecycle.ViewModelProvider(this).get(BooksViewModel::class.java)

        viewModel.booksLiveData.observe(this) {
            it?.let { booksList ->
                with(binding.rvBooks) {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = BooksAdapter(booksList)
                    setHasFixedSize(true)
                }
            }
        }

        viewModel.getBooks()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}