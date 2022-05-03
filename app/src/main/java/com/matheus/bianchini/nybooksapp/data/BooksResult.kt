package com.matheus.bianchini.nybooksapp.data

import com.matheus.bianchini.nybooksapp.data.model.Book

sealed class BooksResult {
    class Success(val books: List<Book>) : BooksResult()
    class ApiError(val statusCode: Int) : BooksResult()
    object ServerError : BooksResult()
}
