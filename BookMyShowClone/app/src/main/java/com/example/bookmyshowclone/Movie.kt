package com.example.bookmyshowclone

import com.google.gson.annotations.SerializedName

data class Movie(
        val id: Int,

        @SerializedName(value = "poster_path")
        val posterPath: String,

        @SerializedName(value = "release_date")
        val releaseDate: String,

        val title: String,

        @SerializedName(value = "vote_average")
        val voteAverage: Double,

        @SerializedName(value = "vote_count")
        val voteCount: Int
)