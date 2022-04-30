package com.matheus.bianchini.nybooksapp.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun getBooks() {
        ApiServices.service.listRepos().enqueue(object : Callback<BooksBodyResponse> {
            override fun onResponse(
                call: Call<BooksBodyResponse>,
                response: Response<BooksBodyResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { booksBodyResponse ->
                        _booksLiveData.postValue(booksBodyResponse.toDomain())
                    }
                }
            }

            override fun onFailure(call: Call<BooksBodyResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}