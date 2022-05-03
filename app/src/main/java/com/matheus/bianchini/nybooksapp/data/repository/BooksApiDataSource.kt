package com.matheus.bianchini.nybooksapp.data.repository

import com.matheus.bianchini.nybooksapp.data.ApiServices
import com.matheus.bianchini.nybooksapp.data.BooksResult
import com.matheus.bianchini.nybooksapp.data.response.BooksBodyResponse
import com.matheus.bianchini.nybooksapp.data.response.toDomain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksApiDataSource : BooksRepository {
    override fun getBooks(booksResultCallback: (result: BooksResult) -> Unit) {
        ApiServices.service.listRepos().enqueue(object : Callback<BooksBodyResponse> {
            override fun onResponse(
                call: Call<BooksBodyResponse>,
                response: Response<BooksBodyResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        response.body()?.let { booksBodyResponse ->
                            booksResultCallback.invoke(BooksResult.Success(booksBodyResponse.toDomain()))
                        }
                    }

                    response.code() == 401 -> {
                        booksResultCallback.invoke(BooksResult.ApiError(response.code()))
                    }
                    else -> {
                        booksResultCallback.invoke(BooksResult.ServerError)
                    }
                }
            }

            override fun onFailure(call: Call<BooksBodyResponse>, t: Throwable) {
                booksResultCallback.invoke(BooksResult.ServerError)
            }
        })
    }
}