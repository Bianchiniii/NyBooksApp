package com.matheus.bianchini.nybooksapp.data

import com.matheus.bianchini.nybooksapp.data.response.BooksBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTServices {

    // TODO: Refatorar
    @GET("lists.json")
    fun listRepos(
        @Query("api-key") apiKey: String = "bJ9hgESJ55uOewwRfkRGxm91PD6jpGGE",
        @Query("list") list: String = "hardcover-fiction"
    ): Call<BooksBodyResponse>
}