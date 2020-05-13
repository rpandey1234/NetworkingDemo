package edu.stanford.rkpandey.networkingdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDatabaseService {
    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") apiKey: String): Call<MoviesResponse>
}