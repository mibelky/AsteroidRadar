package com.udacity.asteroidradar

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    val API_KEY = BuildConfig.NASA_API_KEY
    const val ASTEROIDS_TABLE_NAME = "asteroids"
    const val PICTURE_TABLE_NAME = "pictures"
}

enum class Filters {
    WEEK, SAVED, TODAY
}