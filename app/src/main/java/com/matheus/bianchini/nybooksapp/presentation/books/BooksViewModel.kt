package com.matheus.bianchini.nybooksapp.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheus.bianchini.nybooksapp.R
import com.matheus.bianchini.nybooksapp.data.ApiServices
import com.matheus.bianchini.nybooksapp.data.model.Book
import com.matheus.bianchini.nybooksapp.data.response.BooksBodyResponse
import com.matheus.bianchini.nybooksapp.data.response.toDomain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksViewModel : ViewModel() {

    private val _booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()
    val booksLiveData: LiveData<List<Book>> get() = _booksLiveData

    private val _viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val viewFlipperLiveData: LiveData<Pair<Int, Int?>> get() = _viewFlipperLiveData

    fun getBooks() {
        ApiServices.service.listRepos().enqueue(object : Callback<BooksBodyResponse> {
            override fun onResponse(
                call: Call<BooksBodyResponse>,
                response: Response<BooksBodyResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { booksBodyResponse ->
                        _booksLiveData.postValue(booksBodyResponse.toDomain())
                        _viewFlipperLiveData.postValue(Pair(VIEW_FLIPPER_BOOKS, null))
                    }
                } else if (response.code() == 401) {
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

            override fun onFailure(call: Call<BooksBodyResponse>, t: Throwable) {
                _viewFlipperLiveData.postValue(
                    Pair(
                        VIEW_FLIPPER_ERROR,
                        R.string.error_catastrophic
                    )
                )
            }
        })
    }

    companion object {
        private const val VIEW_FLIPPER_BOOKS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}