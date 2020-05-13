package edu.stanford.rkpandey.networkingdemo

import com.google.gson.annotations.SerializedName

data class MoviesResponse(val results: List<Movie>)

data class Movie(
    val title: String,
    @SerializedName("release_date") val releaseData: String)