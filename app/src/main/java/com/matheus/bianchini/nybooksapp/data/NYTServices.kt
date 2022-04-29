package com.matheus.bianchini.nybooksapp.data

import com.matheus.bianchini.nybooksapp.data.model.Book
import retrofit2.Call
import retrofit2.http.GET

interface NYTServices {

    @GET("list.json")
    fun listRepos(): Call<MutableList<Book>>
}