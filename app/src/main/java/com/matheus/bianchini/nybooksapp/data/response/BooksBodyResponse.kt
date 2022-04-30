package com.matheus.bianchini.nybooksapp.data.response

import com.matheus.bianchini.nybooksapp.data.model.Book
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksBodyResponse(
    @Json(name = "status")
    var status: String,
    @Json(name = "copyright")
    var copyright: String,
    @Json(name = "num_results")
    var numResults: Int,
    @Json(name = "last_modified")
    var lastModified: String,
    @Json(name = "results")
    var booksResults: List<BooksResultsResponse>
)

@JsonClass(generateAdapter = true)
data class BooksResultsResponse(
    @Json(name = "list_name")
    var listName: String,
    @Json(name = "display_name")
    var displayName: String,
    @Json(name = "rank")
    var rank: Int,
    @Json(name = "book_details")
    var bookDetails: List<BooksDetailsResponse>
)

@JsonClass(generateAdapter = true)
data class BooksDetailsResponse(
    @Json(name = "title")
    var title: String,
    @Json(name = "description")
    var description: String,
    @Json(name = "contributor")
    var contributor: String,
    @Json(name = "author")
    var author: String,
    @Json(name = "price")
    var price: Int
)

fun BooksBodyResponse.toDomain(): List<Book> {
    val books: MutableList<Book> = mutableListOf()
    for (result in this.booksResults) {
        with(result.bookDetails.first()) {
            val book = Book(
                title = this.title,
                author = this.author
            )
            books.add(book)
        }
    }
    return books
}


