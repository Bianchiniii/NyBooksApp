package com.matheus.bianchini.nybooksapp.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matheus.bianchini.nybooksapp.R
import com.matheus.bianchini.nybooksapp.data.BooksResult
import com.matheus.bianchini.nybooksapp.data.model.Book
import com.matheus.bianchini.nybooksapp.data.repository.BooksApiDataSource
import com.matheus.bianchini.nybooksapp.data.repository.BooksRepository

class BooksViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    private val _booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()
    val booksLiveData: LiveData<List<Book>> get() = _booksLiveData

    private val _viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val viewFlipperLiveData: LiveData<Pair<Int, Int?>> get() = _viewFlipperLiveData

    fun getBooks() {
        booksRepository.getBooks { result ->
            when (result) {
                is BooksResult.Success -> {
                    _booksLiveData.postValue(result.books)
                    _viewFlipperLiveData.postValue(Pair(VIEW_FLIPPER_BOOKS, null))
                }

                is BooksResult.ApiError -> {
                    if (result.statusCode == 401) {
                        _viewFlipperLiveData.postValue(
                            Pair(
                                VIEW_FLIPPER_ERROR,
                                R.string.error_unauthorized
                            )
                        )
                    } else {
                        _viewFlipperLiveData.postValue(
                            Pair(
                                VIEW_FLIPPER_ERROR,
                                R.string.error_generic
                            )
                        )
                    }
                }

                is BooksResult.ServerError -> {
                    _viewFlipperLiveData.postValue(
                        Pair(
                            VIEW_FLIPPER_ERROR,
                            R.string.error_generic
                        )
                    )
                }
            }
        }
    }

    class BooksFactory(private val dataSource: BooksApiDataSource) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
                return BooksViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val VIEW_FLIPPER_BOOKS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}