package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 *  I had some crashes because of timeouts, so I added this custom OkHttp cleint
 *  Found this solution at stack overflow
 */
private val client = OkHttpClient().newBuilder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

/**
 *  Moshi object for converting picture of the day json response into Kotlin object
 */

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 *  retrofit object with moshi and scalar converters for converting our responses into kotlin object and string
 */

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .client(client)
    .build()

/**
 *  Our API interface with GET queries
 */

interface AsteroidApiService {

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String = Constants.API_KEY): NetworkPictureOfTheDay

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String

}

/**
 *  Singleton of our API
 */

object AsteroidApi {
    val retrofitService: AsteroidApiService = retrofit.create(AsteroidApiService::class.java)
}