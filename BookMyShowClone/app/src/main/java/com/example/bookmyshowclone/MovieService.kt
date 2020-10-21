package com.example.bookmyshowclone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(value = "discover/movie")
    fun getMovies(@Query(value = "api_key")key:String): Call<MovieResponse>
}