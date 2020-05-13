package edu.stanford.rkpandey.networkingdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val NOW_PLAYING_URL = "${BASE_URL}movie/now_playing?api_key=$API_KEY"

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val httpClient = AsyncHttpClient()
//        httpClient.get(
//            NOW_PLAYING_URL,
//            object : JsonHttpResponseHandler() {
//                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
//                    Log.i(TAG, "onSuccess $json")
//                    if (json == null) {
//                        Log.w(TAG, "Did not receive JSON data")
//                        return
//                    }
//                    var movieTitles = ""
//                    val moviesJsonArray = json.jsonObject.getJSONArray("results")
//                    for (i in 0 until moviesJsonArray.length()) {
//                        val movieJsonObj = moviesJsonArray.getJSONObject(i)
//                        val movieTitle = movieJsonObj.getString("title")
//                        Log.i(TAG, "Movie title $movieTitle")
//                        movieTitles += "$movieTitle\n"
//                    }
//                    tvMovies.text = movieTitles
//                }
//
//                override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
//                    Log.e(TAG, "onFailure $statusCode response $response")
//                }
//            })
        Log.i(TAG, "onCreate method!")

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val movieDatabaseService = retrofit.create(MovieDatabaseService::class.java)
        movieDatabaseService.getNowPlaying(API_KEY).enqueue(object: Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                Log.i(TAG, "onResponse $response")
                var movieTitlesString = "Retrofit version!\n"
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    val movieList = body.results
                    for (movie in movieList) {
                        Log.i(TAG, movie.title)
                        movieTitlesString += "${movie.title}\n"
                    }
                }
                tvMovies.text = movieTitlesString
            }

        })
    }
}
