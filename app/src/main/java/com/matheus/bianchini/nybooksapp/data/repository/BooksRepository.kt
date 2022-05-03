package com.matheus.bianchini.nybooksapp.data.repository

import com.matheus.bianchini.nybooksapp.data.BooksResult

interface BooksRepository {
    fun getBooks(booksResultCallback: (result: BooksResult) -> Unit)
}