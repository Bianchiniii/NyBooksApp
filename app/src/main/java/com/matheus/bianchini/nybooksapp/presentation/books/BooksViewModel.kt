package com.matheus.bianchini.nybooksapp.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheus.bianchini.nybooksapp.data.model.Book

class BooksViewModel : ViewModel() {

    private val _booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()
    val booksLiveData: LiveData<List<Book>> get() = _booksLiveData

    fun getBooks() {
        _booksLiveData.value = listOf(
            Book("teste", "teste"),
            Book("cleber", "teste cleber")
        )
    }
}